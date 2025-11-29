package dmitriy.losev.cs.steam.input

import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.request.receive
import io.ktor.server.response.respondText
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import java.io.BufferedWriter
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.concurrent.atomics.AtomicBoolean
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.system.exitProcess
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class HighFrequencyInputRecorder(inputHz: Int = 256) {
    private val inputInterval = 1_000_000_000L / inputHz // Наносекунды

    @OptIn(ExperimentalAtomicApi::class)
    private val isRecording = AtomicBoolean(value = false)
    private val tickCounter = AtomicInteger(0)

    // Состояние клавиш (concurrent для многопоточности)
    private val keysPressed = ConcurrentHashMap<KeyEvent, Boolean>()

    // Координаты мыши (абсолютные)
    private var mouseX = 0
    private var mouseY = 0

    // Предыдущие координаты для расчета дельты
    private var prevMouseX = 0
    private var prevMouseY = 0

    // Буфер для записи
    private val inputBuffer = mutableListOf<InputTick>()
    private val events = mutableListOf<GameEvent>()

    // GSI данные
    private var currentRound = 0
    private var lastPhase: String? = null

    private var startTime = 0L

    // Файлы
    private val inputFilePath = "feature/selenium/data/src/main/resources/%s.csv"
//    private val eventsFile = "feature/selenium/data/src/main/resources/${outputPrefix}_events.json"
//    private val metadataFile = "feature/selenium/data/src/main/resources/${outputPrefix}_metadata.json"

    // Маппинг клавиш
    private val scope = CoroutineScope(context = Dispatchers.Default + SupervisorJob())

    // Флаг инициализации хуков (регистрируем только один раз)
    private var hooksInitialized = false

    // Флаг для отладки мыши (можно включить для проверки)
    private val debugMouse = false
    private var lastMouseUpdate = 0L

    // Input listener
    @OptIn(ExperimentalAtomicApi::class)
    private val inputListener = object : NativeKeyListener, NativeMouseInputListener {

        override fun nativeKeyPressed(nativeKeyEvent: NativeKeyEvent) {
            KeyEvent.getKeyEvent(nativeKeyEvent)?.let { key ->
                keysPressed[key] = true
            }
        }

        override fun nativeKeyReleased(nativeKeyEvent: NativeKeyEvent) {
            KeyEvent.getKeyEvent(nativeKeyEvent)?.let { key ->
                keysPressed[key] = false
            }
        }

        override fun nativeMouseMoved(e: NativeMouseEvent) {
            mouseX = e.x
            mouseY = e.y

            if (debugMouse && System.nanoTime() - lastMouseUpdate > 1_000_000_000L) {
                println("[MOUSE] Moved to: $mouseX, $mouseY")
                lastMouseUpdate = System.nanoTime()
            }
        }

        override fun nativeMousePressed(nativeMouseEvent: NativeMouseEvent) {
            KeyEvent.getKeyEventForMouse(nativeMouseEvent)?.let { key ->
                keysPressed[key] = true
            }
        }

        override fun nativeMouseReleased(nativeMouseEvent: NativeMouseEvent) {
            KeyEvent.getKeyEventForMouse(nativeMouseEvent)?.let { key ->
                keysPressed[key] = false
            }
        }

        override fun nativeMouseClicked(e: NativeMouseEvent) {}
        override fun nativeMouseDragged(e: NativeMouseEvent) {
            mouseX = e.x
            mouseY = e.y

            if (debugMouse && System.nanoTime() - lastMouseUpdate > 1_000_000_000L) {
                println("[MOUSE] Dragged to: $mouseX, $mouseY")
                lastMouseUpdate = System.nanoTime()
            }
        }

        override fun nativeKeyTyped(e: NativeKeyEvent) {}
    }

    @OptIn(ExperimentalAtomicApi::class)
    fun startRecording() {

        println("\n🔴 Начало записи раунда")

        // Инициализируем хуки только один раз за всё время работы
        if (!hooksInitialized) {
            try {
                GlobalScreen.registerNativeHook()
                GlobalScreen.addNativeKeyListener(inputListener)
                GlobalScreen.addNativeMouseListener(inputListener)
                GlobalScreen.addNativeMouseMotionListener(inputListener)
                hooksInitialized = true
                println("✓ Хуки клавиатуры и мыши зарегистрированы")
            } catch (e: Exception) {
                println("❌ Ошибка регистрации native hook: ${e.message}")
                return
            }
        } else {
            println("✓ Хуки уже зарегистрированы, используем существующие")
        }

        isRecording.exchange(newValue = true)
        startTime = System.nanoTime()
        tickCounter.set(0)
        inputBuffer.clear()
        events.clear()

        // ВАЖНО: Очищаем состояние клавиш от предыдущего раунда
        keysPressed.clear()

        // Сбрасываем координаты до центра экрана (для 2560x1440 это 1280x720)
        // Это важно для корректного расчета первой дельты в раунде
        mouseX = 1280
        mouseY = 720
        prevMouseX = 1280
        prevMouseY = 720

        println("✓ Состояние очищено, начинаем запись")
        println("  Координаты мыши сброшены до центра: $mouseX, $mouseY")

        scope.launch {
            recordingLoop()
        }
    }

    @OptIn(ExperimentalAtomicApi::class)
    private suspend fun recordingLoop() {

        var nextTickTime = System.nanoTime()

        while (isRecording.load()) {

            val currentTime = System.nanoTime()

            if (currentTime >= nextTickTime) {

                recordTick()

                nextTickTime += inputInterval
            }

            val sleepTimeMicros = (nextTickTime - System.nanoTime()) / 1_000

            if (sleepTimeMicros > 100) {
                delay(duration = sleepTimeMicros.toDuration(unit = DurationUnit.MICROSECONDS))
            } else if (sleepTimeMicros > 0) {
                delay(duration = 1.toDuration(unit = DurationUnit.MICROSECONDS))
            }
        }
    }

    private fun recordTick() {

        val currentTime = (System.nanoTime() - startTime) / 1_000_000_000.0

        // Вычисляем дельту мыши (относительное движение)
        val deltaX = mouseX - prevMouseX
        val deltaY = mouseY - prevMouseY

        // Обновляем предыдущие координаты
        prevMouseX = mouseX
        prevMouseY = mouseY

        val tick = InputTick(
            tick = tickCounter.getAndIncrement(),
            time = currentTime,
            w = keysPressed.getOrDefault(key = KeyEvent.W, defaultValue = false),
            a = keysPressed.getOrDefault(key = KeyEvent.A, defaultValue = false),
            s = keysPressed.getOrDefault(key = KeyEvent.S, defaultValue = false),
            d = keysPressed.getOrDefault(key = KeyEvent.D, defaultValue = false),
            space = keysPressed.getOrDefault(key = KeyEvent.SPACE, defaultValue = false),
            ctrl = keysPressed.getOrDefault(key = KeyEvent.CTRL, defaultValue = false),
            shift = keysPressed.getOrDefault(key = KeyEvent.SHIFT, defaultValue = false),
            mouse1 = keysPressed.getOrDefault(key = KeyEvent.MOUSE_BUTTON_1, defaultValue = false),
            mouse2 = keysPressed.getOrDefault(key = KeyEvent.MOUSE_BUTTON_2, defaultValue = false),
            mouse3 = keysPressed.getOrDefault(key = KeyEvent.MOUSE_BUTTON_3, defaultValue = false),
            mouseX = deltaX,  // Теперь это дельта, а не абсолютная координата!
            mouseY = deltaY,  // Теперь это дельта, а не абсолютная координата!
            r = keysPressed.getOrDefault(key = KeyEvent.R, defaultValue = false),
            e = keysPressed.getOrDefault(key = KeyEvent.E, defaultValue = false),
            q = keysPressed.getOrDefault(key = KeyEvent.Q, defaultValue = false),
            g = keysPressed.getOrDefault(key = KeyEvent.G, defaultValue = false),
            b = keysPressed.getOrDefault(key = KeyEvent.B, defaultValue = false),
            slot1 = keysPressed.getOrDefault(key = KeyEvent.SLOT_1, defaultValue = false),
            slot2 = keysPressed.getOrDefault(key = KeyEvent.SLOT_2, defaultValue = false),
            slot3 = keysPressed.getOrDefault(key = KeyEvent.SLOT_3, defaultValue = false),
            slot4 = keysPressed.getOrDefault(key = KeyEvent.SLOT_4, defaultValue = false),
            slot5 = keysPressed.getOrDefault(key = KeyEvent.SLOT_5, defaultValue = false),
            tab = keysPressed.getOrDefault(key = KeyEvent.TAB, defaultValue = false),
            esc = keysPressed.getOrDefault(key = KeyEvent.ESC, defaultValue = false),
            f = keysPressed.getOrDefault(key = KeyEvent.F, defaultValue = false)
        )

        synchronized(lock = inputBuffer) {
            inputBuffer.add(tick)
        }
    }

    private fun processGSIEvent(jsonData: String) {
        try {
            val json = Json { ignoreUnknownKeys = true }
            val data = json.parseToJsonElement(jsonData).jsonObject

            val currentTime = (System.nanoTime() - startTime) / 1_000_000_000.0

            // Извлекаем фазу раунда
            val round = data["round"]?.jsonObject
            val phase = round?.get("phase")?.jsonPrimitive?.content

            // Начало раунда
            if (phase == "live" && lastPhase != "live") {
                currentRound++
                events.add(
                    GameEvent(
                        type = "round_start",
                        timestamp = currentTime,
                        round = currentRound,
                        data = mapOf("phase" to phase)
                    )
                )
                println("\n[EVENT] Раунд $currentRound начался")
            }

            // Конец раунда
            else if ((phase == "over" || phase == "freezetime") && lastPhase == "live") {
                val winTeam = round["win_team"]?.jsonPrimitive?.content ?: "unknown"
                events.add(
                    GameEvent(
                        type = "round_end",
                        timestamp = currentTime,
                        round = currentRound,
                        data = mapOf("win_team" to winTeam, "phase" to phase)
                    )
                )
                println("\n[EVENT] Раунд $currentRound закончился")
            }

            lastPhase = phase

            // Смерть игрока
            val player = data["player"]?.jsonObject
            val health = player?.get("state")?.jsonObject?.get("health")?.jsonPrimitive?.intOrNull ?: 100

            if (health == 0 && events.lastOrNull()?.type != "player_death") {
                events.add(
                    GameEvent(
                        type = "player_death",
                        timestamp = currentTime,
                        round = currentRound
                    )
                )
                println("\n[EVENT] Игрок умер")
            }

        } catch (e: Exception) {
            // Игнорируем ошибки парсинга
        }
    }

    @OptIn(ExperimentalAtomicApi::class)
    suspend fun stopRecording(gameId: Int, roundNumber: Int, steamId: Long) {
        if (!isRecording.load()) {
            println("⚠️ Запись не была запущена")
            return
        }

        println("\n🛑 Остановка записи раунда $roundNumber")

        isRecording.exchange(newValue = false)

        delay(100) // Даём время на завершение записи

        // НЕ останавливаем listeners - они будут работать для следующего раунда
        // Хуки остаются зарегистрированными до конца работы программы

        println("✓ Записано ${inputBuffer.size} тиков")

        // Проверяем, есть ли нажатия клавиш
        val hasKeyPresses = inputBuffer.any { tick ->
            tick.w || tick.a || tick.s || tick.d || tick.space ||
            tick.ctrl || tick.shift || tick.r || tick.e || tick.q ||
            tick.g || tick.b || tick.slot1 || tick.slot2 || tick.slot3 ||
            tick.slot4 || tick.slot5 || tick.tab || tick.esc || tick.f ||
            tick.mouse1 || tick.mouse2 || tick.mouse3
        }

        if (!hasKeyPresses) {
            println("⚠️ ВНИМАНИЕ: В записи нет ни одного нажатия клавиш!")
        } else {
            println("✓ Записаны нажатия клавиш")
        }

        // Проверяем движение мыши
        val uniqueMousePositions = inputBuffer.map { Pair(it.mouseX, it.mouseY) }.distinct()
        if (uniqueMousePositions.size == 1) {
            println("⚠️ ВНИМАНИЕ: Мышь не двигалась! Координаты: ${uniqueMousePositions.first()}")
            println("   Возможно игра захватила курсор в полноэкранном режиме")
        } else {
            println("✓ Записано ${uniqueMousePositions.size} уникальных позиций мыши")
        }

        // Сохраняем данные
        saveAllData(gameId, roundNumber, steamId)
    }

    private fun saveAllData(gameId: Int, roundNumber: Int, steamId: Long) {

        synchronized(lock = inputBuffer) {

            if (inputBuffer.isNotEmpty()) {

                File(inputFilePath.format("game - $gameId, steamId - $steamId, round - $roundNumber")).bufferedWriter(charset = Charsets.UTF_8).use { writer ->
                    writeHeadersInFile(writer)
                    writeDataInFile(writer = writer, ticks = inputBuffer)
                }
            }
        }

        // 2. Сохраняем события
//        if (events.isNotEmpty()) {
//            val json = Json { prettyPrint = true }
//            File(eventsFile).writeText(json.encodeToString(events))
//            println("✓ События сохранены: $eventsFile (${events.size} событий)")
//        }
//
//        // 3. Метаданные
//        val duration = (System.nanoTime() - startTime) / 1_000_000_000.0
//        val metadata = RecordingMetadata(
//            recordingStart = LocalDateTime.now().toString(),
//            durationSec = duration,
//            inputHz = inputHz,
//            totalTicks = tickCounter.get(),
//            totalEvents = events.size,
//            roundsPlayed = currentRound
//        )
//
//        val json = Json { prettyPrint = true }
//        File(metadataFile).writeText(json.encodeToString(metadata))
//        println("✓ Метаданные: $metadataFile")
    }

    private fun writeHeadersInFile(writer: BufferedWriter) {
        // Заголовки для InputTick: сначала клавиши, потом координаты мыши
        val keyHeaders = listOf("W", "A", "S", "D", "SPACE", "CTRL", "SHIFT",
                                "MOUSE_BUTTON_1", "MOUSE_BUTTON_2", "MOUSE_BUTTON_3",
                                "MOUSE_X", "MOUSE_Y",
                                "R", "E", "Q", "G", "B",
                                "SLOT_1", "SLOT_2", "SLOT_3", "SLOT_4", "SLOT_5",
                                "TAB", "ESC", "F")
        val headers = listOf("tick", "time") + keyHeaders
        writer.write(headers.joinToString(separator = ","))
        writer.newLine()
    }

    private fun writeDataInFile(writer: BufferedWriter, ticks: List<InputTick>) {
        ticks.forEach { tick ->
            writer.write(tick.toString())
            writer.newLine()
        }
    }

    companion object {
        private fun generateTimestamp(): String {
            return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
                .let { "recording_$it" }
        }
    }
}