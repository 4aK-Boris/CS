package dmitriy.losev.cs

import java.time.DayOfWeek
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.ConcurrentHashMap
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

/**
 * Расширенный планировщик корутин с:
 *  - глобальным старт-барьером: набор пререквизитов, которые должны один раз успешно завершиться до старта остальных задач;
 *  - локальными зависимостями dependsOnOnce (для строго порядка между отдельными задачами);
 *  - ограничением параллелизма на уровне задачи.
 */
class AdvancedCoroutineScheduler(private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default) : CoroutineScope {

    override val coroutineContext = SupervisorJob() + defaultDispatcher

    private val tasks = ConcurrentHashMap<String, Job>()
    private val taskDispatchers = ConcurrentHashMap<String, CoroutineDispatcher>()
    private val taskExecutionCounts = ConcurrentHashMap<String, Int>()
    private val taskConfigs = ConcurrentHashMap<String, TaskConfig>()

    // ---------- Глобальный старт-барьер ----------
    @Volatile
    private var prerequisiteTasks: Set<String> = emptySet()
    @Volatile
    private var startBarrier: CompletableDeferred<Unit>? = null

    // ---------- Маркеры "один раз успешно завершилась" + сигналы ожидания ----------
    private val completedOnce = ConcurrentHashMap<String, Boolean>()
    private val completedOnceSignal = ConcurrentHashMap<String, CompletableDeferred<Unit>>()

    /** Создаёт/получает сигнал завершения для taskId. */
    private fun signalOf(taskId: String): CompletableDeferred<Unit> =
        completedOnceSignal.computeIfAbsent(taskId) { CompletableDeferred() }

    /** Установить глобальные пререквизиты (должны один раз успешно завершиться). */
    @Synchronized
    fun setStartupPrerequisites(vararg taskIds: String) {
        prerequisiteTasks = taskIds.toSet()
        // Обнуляем фиксацию завершения и сигналы (переустановка барьера предполагается на старте приложения)
        completedOnce.clear()
        completedOnceSignal.clear()
        startBarrier = if (prerequisiteTasks.isEmpty()) null else CompletableDeferred()
    }

    /** Принудительно открыть барьер (аварийный обход). */
    fun openBarrierNow() { startBarrier?.complete(Unit) }

    /** Ожидать глобальный барьер, если задача не пререквизит. */
    private suspend fun awaitStartBarrierIfNeeded(taskId: String) {
        val barrier = startBarrier ?: return
        if (taskId !in prerequisiteTasks) barrier.await()
    }

    /** Ожидать локальные dependsOnOnce для конкретной задачи. */
    private suspend fun awaitDependsOnOnce(config: TaskConfig) {
        for (dep in config.dependsOnOnce) {
            if (completedOnce[dep] == true) continue
            signalOf(dep).await()
        }
    }

    // -----------------------------------------------------------
    //                    Планирование задач
    // -----------------------------------------------------------

    /** Запуск функции с определенной периодичностью. */
    fun scheduleWithPeriod(
        taskId: String,
        periodMillis: Long,
        initialDelayMillis: Long = 0,
        config: TaskConfig = TaskConfig(),
        task: suspend () -> Unit
    ) {
        cancelTask(taskId)
        setupTaskDispatcher(taskId, config)

        val job = launch {
            delay(timeMillis = initialDelayMillis)
            while (isActive) {
                if (config.skipIfRunning && getTaskConcurrency(taskId) >= config.maxConcurrency) {
                    println("Пропускаем запуск $taskId — все слоты заняты")
                } else {
                    // Ждём локальные зависимости и глобальный барьер
                    awaitDependsOnOnce(config)
                    awaitStartBarrierIfNeeded(taskId)

                    launch(context = taskDispatchers[taskId]!!) {
                        executeTask(taskId, task)
                    }
                }
                delay(timeMillis = periodMillis)
            }
        }
        tasks[taskId] = job
    }

    /** Запуск функции с расписанием (рабочие часы и т.д.). */
    fun scheduleWithTimeConstraints(
        taskId: String,
        periodMinutes: Long,
        schedule: Schedule,
        config: TaskConfig = TaskConfig(),
        task: suspend () -> Unit
    ) {
        cancelTask(taskId)
        setupTaskDispatcher(taskId, config)

        val job = launch {
            val initialDelay = calculateInitialDelay(schedule)
            delay(timeMillis = initialDelay)

            while (isActive) {
                val now = ZonedDateTime.now(schedule.timeZone)
                if (schedule.isTimeAllowed(now)) {
                    if (config.skipIfRunning && getTaskConcurrency(taskId) >= config.maxConcurrency) {
                        println("Пропускаем запуск $taskId — все слоты заняты")
                    } else {
                        awaitDependsOnOnce(config)
                        awaitStartBarrierIfNeeded(taskId)

                        launch(context = taskDispatchers[taskId]!!) {
                            executeTask(taskId, task)
                        }
                    }
                }
                delay(timeMillis = periodMinutes * 60 * 1000)
            }
        }
        tasks[taskId] = job
    }

    /** Запуск функции еженедельно. */
    fun scheduleWeekly(
        taskId: String,
        dayOfWeek: DayOfWeek,
        time: LocalTime,
        timeZone: ZoneId = ZoneId.systemDefault(),
        config: TaskConfig = TaskConfig(),
        task: suspend () -> Unit
    ) {
        cancelTask(taskId)
        setupTaskDispatcher(taskId, config)

        val job = launch {
            while (isActive) {
                val now = ZonedDateTime.now(timeZone)
                val nextRun = getNextWeeklyRun(now, dayOfWeek, time)
                val delayMillis = ChronoUnit.MILLIS.between(now, nextRun)

                delay(timeMillis = delayMillis)

                if (isActive) {
                    awaitDependsOnOnce(config)
                    awaitStartBarrierIfNeeded(taskId)

                    launch(context = taskDispatchers[taskId]!!) {
                        executeTask(taskId, task)
                    }
                }
            }
        }
        tasks[taskId] = job
    }

    /** Запуск функции ежедневно. */
    fun scheduleDaily(
        taskId: String,
        time: LocalTime,
        timeZone: ZoneId = ZoneId.systemDefault(),
        config: TaskConfig = TaskConfig(),
        task: suspend () -> Unit
    ) {
        cancelTask(taskId)
        setupTaskDispatcher(taskId, config)

        val job = launch {
            while (isActive) {
                val now = ZonedDateTime.now(timeZone)
                val todayRun = now.with(time)
                val nextRun = if (todayRun.isAfter(now)) todayRun else todayRun.plusDays(1)
                val delayMillis = ChronoUnit.MILLIS.between(now, nextRun)

                delay(timeMillis = delayMillis)

                if (isActive) {
                    awaitDependsOnOnce(config)
                    awaitStartBarrierIfNeeded(taskId)

                    launch(context = taskDispatchers[taskId]!!) {
                        executeTask(taskId, task)
                    }
                }
            }
        }
        tasks[taskId] = job
    }

    /** Однократный запуск. */
    fun scheduleOnce(
        taskId: String,
        delayMillis: Long,
        config: TaskConfig = TaskConfig(),
        task: suspend () -> Unit
    ) {
        cancelTask(taskId)
        setupTaskDispatcher(taskId, config)

        val job = launch {
            delay(timeMillis = delayMillis)
            awaitDependsOnOnce(config)
            awaitStartBarrierIfNeeded(taskId)

            launch(context = taskDispatchers[taskId]!!) {
                executeTask(taskId, task)
            }
            tasks.remove(key = taskId)
        }
        tasks[taskId] = job
    }

    // -----------------------------------------------------------
    //                        Сервисные методы
    // -----------------------------------------------------------

    /** Текущее число выполняющихся экземпляров задачи. */
    fun getTaskConcurrency(taskId: String): Int = taskExecutionCounts[taskId] ?: 0

    /** Информация по всем задачам. */
    fun getTasksInfo(): Map<String, TaskInfo> =
        tasks.keys.associateWith { taskId ->
            TaskInfo(
                taskId = taskId,
                isScheduled = tasks.containsKey(taskId),
                currentConcurrency = getTaskConcurrency(taskId),
                maxConcurrency = taskConfigs[taskId]?.maxConcurrency ?: 1
            )
        }

    /** Отмена конкретной задачи. */
    fun cancelTask(taskId: String) {
        tasks[taskId]?.cancel()
        tasks.remove(key = taskId)
        taskDispatchers.remove(key = taskId)
        taskExecutionCounts.remove(key = taskId)
        taskConfigs.remove(key = taskId)
        // Барьер и completedOnce не трогаем.
    }

    /** Остановка всего планировщика. */
    suspend fun shutdown() {
        tasks.values.forEach { task -> task.cancelAndJoin() }
        coroutineContext.cancel()
    }

    // -----------------------------------------------------------
    //                   Внутренняя логика
    // -----------------------------------------------------------

    private fun setupTaskDispatcher(taskId: String, config: TaskConfig) {
        taskConfigs[taskId] = config
        taskDispatchers[taskId] = defaultDispatcher.limitedParallelism(config.maxConcurrency)
        taskExecutionCounts[taskId] = 0
    }

    private suspend fun executeTask(taskId: String, task: suspend () -> Unit) {
        try {
            taskExecutionCounts.compute(taskId) { _, count -> (count ?: 0) + 1 }
            println("Начало $taskId (активных: ${getTaskConcurrency(taskId)})")

            task()

            // Помечаем как "один раз успешно завершилась" и сигналим ожидающим
            if (completedOnce.putIfAbsent(taskId, true) != true) {
                completedOnceSignal[taskId]?.complete(Unit)
            }

            // Если taskId — пререквизит, возможно открываем глобальный барьер
            if (taskId in prerequisiteTasks) {
                val barrier = startBarrier
                if (barrier != null && prerequisiteTasks.all { completedOnce[it] == true }) {
                    println("Все пререквизиты выполнены: ${prerequisiteTasks.joinToString()}. Открываем барьер.")
                    barrier.complete(Unit)
                }
            }

            println("Конец $taskId")
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            println("Ошибка в задаче $taskId: ${e.message}")
        } finally {
            taskExecutionCounts.compute(taskId) { _, count -> ((count ?: 1) - 1).coerceAtLeast(0) }
        }
    }

    private fun calculateInitialDelay(schedule: Schedule): Long {
        val now = ZonedDateTime.now(schedule.timeZone)
        var next = now
        while (!schedule.isTimeAllowed(dateTime = next)) {
            next = next.plusMinutes(1)
        }
        return ChronoUnit.MILLIS.between(now, next).coerceAtLeast(0)
    }

    private fun getNextWeeklyRun(now: ZonedDateTime, dayOfWeek: DayOfWeek, time: LocalTime): ZonedDateTime {
        var next = now.with(time)
        if (now.dayOfWeek == dayOfWeek && next.isAfter(now)) return next
        while (next.dayOfWeek != dayOfWeek || !next.isAfter(now)) {
            next = next.plusDays(1)
        }
        return next
    }
}
