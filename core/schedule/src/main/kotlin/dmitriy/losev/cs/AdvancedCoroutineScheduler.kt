package dmitriy.losev.cs

import dmitriy.losev.cs.tasks.TaskExecutionLog
import dmitriy.losev.cs.tasks.TaskLogger
import dmitriy.losev.cs.tasks.TaskStatus
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean
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
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Планировщик корутин с поддержкой:
 * - Единовременного и периодического запуска задач
 * - Пропуска задачи, если предыдущая ещё выполняется
 * - Зависимостей между задачами (ожидание/пропуск)
 * - Логирования через TaskLogger (можно реализовать для БД)
 * - Обработки Result и List<Result> через TaskResult
 */
class AdvancedCoroutineScheduler(defaultDispatcher: CoroutineDispatcher = Dispatchers.Default, private val logger: TaskLogger) : CoroutineScope {

    override val coroutineContext = SupervisorJob() + defaultDispatcher

    private val tasks = ConcurrentHashMap<String, Job>()
    private val runningTasks = ConcurrentHashMap<String, AtomicBoolean>()
    private val taskCompletionSignals = ConcurrentHashMap<String, CompletableDeferred<Unit>>()
    private val taskConfigs = ConcurrentHashMap<String, TaskConfig>()

    /**
     * Запуск задачи с периодичностью.
     * Если предыдущий запуск ещё выполняется — новый пропускается.
     *
     * @param taskId уникальный идентификатор задачи
     * @param period интервал между запусками (например: 5.minutes, 1.hours, 1.days)
     * @param initialDelay начальная задержка перед первым запуском
     * @param config конфигурация задачи (зависимости, пропуски)
     * @param task функция задачи, возвращающая TaskResult
     */
    fun <T> scheduleWithPeriod(
        taskId: String,
        period: Duration,
        initialDelay: Duration = 0.seconds,
        config: TaskConfig = TaskConfig(),
        task: suspend () -> TaskResult<T>
    ) {
        cancelTask(taskId)
        runningTasks[taskId] = AtomicBoolean(false)
        taskConfigs[taskId] = config

        val job = launch {
            delay(initialDelay)

            while (isActive) {
                executeWithDependencies(taskId, config, task)
                delay(period)
            }
        }

        tasks[taskId] = job
    }

    /**
     * Однократный запуск задачи.
     *
     * @param taskId уникальный идентификатор задачи
     * @param delay задержка перед запуском (например: 10.seconds, 1.minutes)
     * @param config конфигурация задачи (зависимости, пропуски)
     * @param task функция задачи, возвращающая TaskResult
     */
    fun <T> scheduleOnce(
        taskId: String,
        delay: Duration = 0.seconds,
        config: TaskConfig = TaskConfig(),
        task: suspend () -> TaskResult<T>
    ) {
        cancelTask(taskId)
        runningTasks[taskId] = AtomicBoolean(false)
        taskConfigs[taskId] = config

        val job = launch {
            delay(delay)
            executeWithDependencies(taskId, config, task)
            tasks.remove(taskId)
            runningTasks.remove(taskId)
            taskConfigs.remove(taskId)
        }

        tasks[taskId] = job
    }

    /**
     * Запуск задачи немедленно (без планирования).
     * Если задача уже выполняется — пропускается.
     */
    suspend fun <T> runNow(
        taskId: String,
        config: TaskConfig = TaskConfig(),
        task: suspend () -> TaskResult<T>
    ) {
        runningTasks.putIfAbsent(taskId, AtomicBoolean(false))
        taskConfigs.putIfAbsent(taskId, config)
        executeWithDependencies(taskId, config, task)
    }

    /**
     * Проверяет, выполняется ли задача в данный момент.
     */
    fun isTaskRunning(taskId: String): Boolean {
        return runningTasks[taskId]?.get() ?: false
    }

    /**
     * Проверяет, запланирована ли задача.
     */
    fun isTaskScheduled(taskId: String): Boolean {
        return tasks.containsKey(taskId)
    }

    /**
     * Информация о всех задачах.
     */
    fun getTasksInfo(): Map<String, TaskInfo> {
        return tasks.keys.associateWith { taskId ->
            TaskInfo(
                taskId = taskId,
                isScheduled = true,
                currentConcurrency = if (isTaskRunning(taskId)) 1 else 0,
                maxConcurrency = 1
            )
        }
    }

    /**
     * Отмена задачи по идентификатору.
     */
    fun cancelTask(taskId: String) {
        tasks[taskId]?.cancel()
        tasks.remove(taskId)
        runningTasks.remove(taskId)
        taskConfigs.remove(taskId)
        taskCompletionSignals.remove(taskId)
    }

    /**
     * Остановка всего планировщика.
     */
    suspend fun shutdown() {
        tasks.values.forEach { job -> job.cancelAndJoin() }
        coroutineContext.cancel()
    }

    /**
     * Получить или создать сигнал завершения для задачи.
     */
    private fun getOrCreateCompletionSignal(taskId: String): CompletableDeferred<Unit> {
        return taskCompletionSignals.computeIfAbsent(taskId) { CompletableDeferred() }
    }

    /**
     * Выполнение задачи с учётом зависимостей.
     */
    private suspend fun <T> executeWithDependencies(
        taskId: String,
        config: TaskConfig,
        task: suspend () -> TaskResult<T>
    ) {
        // Проверяем skipIfActive — пропускаем, если указанные задачи активны
        for (blockerTaskId in config.skipIfActive) {
            if (isTaskRunning(blockerTaskId)) {
                logSkipped(taskId, "Task $blockerTaskId is active")
                return
            }
        }

        // Ожидаем завершения задач из awaitCompletion
        for (dependencyTaskId in config.awaitCompletion) {
            if (isTaskRunning(dependencyTaskId)) {
                getOrCreateCompletionSignal(dependencyTaskId).await()
            }
        }

        executeTaskIfNotRunning(taskId, task)
    }

    private suspend fun <T> executeTaskIfNotRunning(taskId: String, task: suspend () -> TaskResult<T>) {
        val isRunning = runningTasks[taskId] ?: AtomicBoolean(false)

        if (!isRunning.compareAndSet(false, true)) {
            logSkipped(taskId, "Task is already running")
            return
        }

        // Создаём новый сигнал для этого запуска
        taskCompletionSignals[taskId] = CompletableDeferred()

        val startedAt = Instant.now()
        logStarted(taskId, startedAt)

        try {
            val result = task()
            val finishedAt = Instant.now()
            val durationMillis = finishedAt.toEpochMilli() - startedAt.toEpochMilli()

            if (result.hasErrors) {
                logFailed(taskId, startedAt, finishedAt, durationMillis, result)
            } else {
                logCompleted(taskId, startedAt, finishedAt, durationMillis, result)
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            val finishedAt = Instant.now()
            val durationMillis = finishedAt.toEpochMilli() - startedAt.toEpochMilli()
            logFailed(taskId, startedAt, finishedAt, durationMillis, e)
        } finally {
            isRunning.set(false)
            // Сигнализируем о завершении
            taskCompletionSignals[taskId]?.complete(Unit)
        }
    }

    private suspend fun logStarted(taskId: String, startedAt: Instant) {
        val log = TaskExecutionLog(
            taskId = taskId,
            status = TaskStatus.STARTED,
            startedAt = startedAt
        )
        logger.onTaskStarted(log)
    }

    private suspend fun logCompleted(
        taskId: String,
        startedAt: Instant,
        finishedAt: Instant,
        durationMillis: Long,
        result: TaskResult<*>
    ) {
        val log = TaskExecutionLog(
            taskId = taskId,
            status = TaskStatus.COMPLETED,
            startedAt = startedAt,
            finishedAt = finishedAt,
            durationMillis = durationMillis,
            successCount = result.successCount,
            errorCount = result.errorCount,
            totalCount = result.totalCount,
            data = result.data.toString()
        )
        logger.onTaskCompleted(log)
    }

    private suspend fun logFailed(
        taskId: String,
        startedAt: Instant,
        finishedAt: Instant,
        durationMillis: Long,
        result: TaskResult<*>
    ) {
        val errorMessage = result.errors.joinToString(separator = "; ") { error ->
            if (error.context != null) "[${error.context}] ${error.message}" else error.message
        }

        val log = TaskExecutionLog(
            taskId = taskId,
            status = TaskStatus.FAILED,
            startedAt = startedAt,
            finishedAt = finishedAt,
            durationMillis = durationMillis,
            errorMessage = errorMessage,
            successCount = result.successCount,
            errorCount = result.errorCount,
            totalCount = result.totalCount
        )
        logger.onTaskFailed(log)
    }

    private suspend fun logFailed(
        taskId: String,
        startedAt: Instant,
        finishedAt: Instant,
        durationMillis: Long,
        exception: Exception
    ) {
        val log = TaskExecutionLog(
            taskId = taskId,
            status = TaskStatus.FAILED,
            startedAt = startedAt,
            finishedAt = finishedAt,
            durationMillis = durationMillis,
            errorMessage = exception.message ?: "Unknown error",
            errorCount = 1,
            totalCount = 1
        )
        logger.onTaskFailed(log)
    }

    private suspend fun logSkipped(taskId: String, reason: String? = null) {
        val log = TaskExecutionLog(
            taskId = taskId,
            status = TaskStatus.SKIPPED,
            startedAt = Instant.now(),
            errorMessage = reason
        )
        logger.onTaskSkipped(log)
    }
}
