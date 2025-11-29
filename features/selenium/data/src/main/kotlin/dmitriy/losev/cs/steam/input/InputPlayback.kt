package dmitriy.losev.cs.steam.input

import java.awt.MouseInfo
import java.awt.Robot
import java.awt.event.InputEvent
import java.awt.event.KeyEvent.VK_1
import java.io.File
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.ConcurrentHashMap

/**
 * Класс для воспроизведения записанных InputTick
 */
class InputPlayback(
    private val playbackHz: Int = 1000,  // Частота воспроизведения
    private val debugMode: Boolean = false  // Режим отладки с логированием
) {

    private val robot = Robot().apply {
        autoDelay = 0
        isAutoWaitForIdle = false
    }

    private val isPlaying = AtomicBoolean(false)
    private val playbackInterval = 1_000_000_000L / playbackHz  // Наносекунды

    // Текущее состояние зажатых клавиш
    private val pressedKeys = ConcurrentHashMap<Int, Boolean>()
    private val pressedMouseButtons = ConcurrentHashMap<Int, Boolean>()

    private lateinit var ticks: List<InputTick>

    fun loadTicks(filePath: String) {
        ticks = File(filePath)
            .readLines()
            .drop(1)
            .map { line ->
                InputTick.toInputTick(line)
            }

        if (debugMode) {
            println("\n[DEBUG] Загружено ${ticks.size} тиков")
            if (ticks.isNotEmpty()) {
                println("[DEBUG] Первый тик: ${ticks.first()}")
                println("[DEBUG] Последний тик: ${ticks.last()}")

                // Найти первый тик с нажатой клавишей
                val firstKeyPress = ticks.firstOrNull { tick ->
                    tick.w || tick.a || tick.s || tick.d || tick.space ||
                    tick.ctrl || tick.shift || tick.r || tick.e || tick.q ||
                    tick.g || tick.b || tick.slot1 || tick.slot2 || tick.slot3 ||
                    tick.slot4 || tick.slot5 || tick.tab || tick.esc || tick.f ||
                    tick.mouse1 || tick.mouse2 || tick.mouse3
                }
                if (firstKeyPress != null) {
                    println("[DEBUG] Первое нажатие клавиши: $firstKeyPress")
                } else {
                    println("[DEBUG] ⚠️ НЕТ НИ ОДНОГО НАЖАТИЯ КЛАВИШ В ФАЙЛЕ!")
                }
            }
        }
    }

    /**
     * Начать воспроизведение
     */
    suspend fun play(delaySeconds: Int = 5) {
        if (isPlaying.get()) {
            println("❌ Воспроизведение уже идёт!")
            return
        }

        if (ticks.isEmpty()) {
            println("❌ Нет данных для воспроизведения!")
            return
        }

        println("\n${"=".repeat(70)}")
        println("🎬 InputPlayback")
        println("=".repeat(70))
        println("Частота: $playbackHz Hz")
        println("Тиков: ${ticks.size}")
        println("Длительность: ${"%.2f".format(ticks.last().time)} секунд")
        println("=".repeat(70))

        println("\n🎬 ВОСПРОИЗВЕДЕНИЕ НАЧАЛОСЬ!\n")
        isPlaying.set(true)

        // Воспроизводим
        playbackLoop(ticks)

        // Очищаем состояние
        releaseAll()

        println("\n✅ Воспроизведение завершено!")
        isPlaying.set(false)
    }

    /**
     * Остановить воспроизведение
     */
    fun stop() {
        if (!isPlaying.get()) {
            println("\n⚠️  Воспроизведение уже остановлено")
            return
        }

        println("\n⏹  Остановка воспроизведения...")
        isPlaying.set(false)

        // Даём немного времени на завершение текущего тика
        Thread.sleep(50)

        releaseAll()
    }

    /**
     * Основной цикл воспроизведения
     */
    private suspend fun playbackLoop(ticks: List<InputTick>) {
        val startTime = System.nanoTime()

        for ((index, tick) in ticks.withIndex()) {
            if (!isPlaying.get()) break

            // Целевое время для этого тика (в наносекундах от старта)
            val targetTime = (tick.time * 1_000_000_000).toLong()
            val targetNanoTime = startTime + targetTime

            // Ждём пока не наступит время этого тика
            while (System.nanoTime() < targetNanoTime) {
                val sleepTimeMicros = (targetNanoTime - System.nanoTime()) / 1_000

                if (sleepTimeMicros > 100) {
                    delay(sleepTimeMicros / 1000) // Миллисекунды
                } else if (sleepTimeMicros > 0) {
                    delay(1)
                } else {
                    break
                }
            }

            // Воспроизводим тик
            playTick(tick)

            // Прогресс каждую секунду
            if (index % playbackHz == 0) {
                val progress = (index.toDouble() / ticks.size * 100).toInt()
                print("\r⏵  Прогресс: $progress% | Время: ${"%.2f".format(tick.time)}s")
            }
        }
    }

    /**
     * Воспроизвести один тик
     */
    private fun playTick(tick: InputTick) {
        // Клавиши
        processKey(KeyEvent.W, tick.w)
        processKey(KeyEvent.A, tick.a)
        processKey(KeyEvent.S, tick.s)
        processKey(KeyEvent.D, tick.d)
        processKey(KeyEvent.SPACE, tick.space)
        processKey(KeyEvent.CTRL, tick.ctrl)
        processKey(KeyEvent.SHIFT, tick.shift)
        processKey(KeyEvent.R, tick.r)
        processKey(KeyEvent.E, tick.e)
        processKey(KeyEvent.Q, tick.q)
        processKey(KeyEvent.G, tick.g)
        processKey(KeyEvent.B, tick.b)
        processKey(KeyEvent.SLOT_1, tick.slot1)
        processKey(KeyEvent.SLOT_2, tick.slot2)
        processKey(KeyEvent.SLOT_3, tick.slot3)
        processKey(KeyEvent.SLOT_4, tick.slot4)
        processKey(KeyEvent.SLOT_5, tick.slot5)
        processKey(KeyEvent.TAB, tick.tab)
        processKey(KeyEvent.ESC, tick.esc)
        processKey(KeyEvent.F, tick.f)

        // Мышь - кнопки
        processMouseButton(InputEvent.BUTTON1_DOWN_MASK, tick.mouse1)
        processMouseButton(InputEvent.BUTTON3_DOWN_MASK, tick.mouse2)
        processMouseButton(InputEvent.BUTTON2_DOWN_MASK, tick.mouse3)

        // Мышь - движение (теперь это ДЕЛЬТЫ - относительное движение!)
        // Получаем текущую позицию и добавляем дельты
        if (tick.mouseX != 0 || tick.mouseY != 0) {
            val currentPoint = MouseInfo.getPointerInfo().location
            val newX = currentPoint.x + tick.mouseX
            val newY = currentPoint.y + tick.mouseY
            robot.mouseMove(newX, newY)
        }
    }

    /**
     * Обработка клавиши
     */
    private fun processKey(keyEvent: KeyEvent, state: Boolean) {

        val wasPressed = pressedKeys[keyEvent.awtKeyCode] ?: false

        if (state && !wasPressed) {
            // Нажать
            robot.keyPress(keyEvent.awtKeyCode)
            pressedKeys[keyEvent.awtKeyCode] = true
            if (debugMode) {
                println("[DEBUG] Key PRESS: ${keyEvent.name} (awt code: ${keyEvent.awtKeyCode})")
            }
        } else if (!state && wasPressed) {
            // Отпустить
            robot.keyRelease(keyEvent.awtKeyCode)
            pressedKeys.remove(keyEvent.awtKeyCode)
            if (debugMode) {
                println("[DEBUG] Key RELEASE: ${keyEvent.name} (awt code: ${keyEvent.awtKeyCode})")
            }
        }
    }

    /**
     * Обработка кнопки мыши
     */
    private fun processMouseButton(mask: Int, state: Boolean) {
        val wasPressed = pressedMouseButtons[mask] ?: false

        if (state && !wasPressed) {
            robot.mousePress(mask)
            pressedMouseButtons[mask] = true
            if (debugMode) {
                println("[DEBUG] Mouse PRESS: mask=$mask")
            }
        } else if (!state && wasPressed) {
            robot.mouseRelease(mask)
            pressedMouseButtons.remove(mask)
            if (debugMode) {
                println("[DEBUG] Mouse RELEASE: mask=$mask")
            }
        }
    }

    /**
     * Отпустить все клавиши и кнопки
     */
    private fun releaseAll() {
        println("\n🧹 Очистка состояния...")

        val keysToRelease = pressedKeys.keys.toList()
        val mouseToRelease = pressedMouseButtons.keys.toList()

        if (debugMode) {
            println("[DEBUG] Отпускаем ${keysToRelease.size} клавиш и ${mouseToRelease.size} кнопок мыши")
        }

        // Отпускаем все клавиши
        keysToRelease.forEach { keyCode ->
            try {
                robot.keyRelease(keyCode)
                if (debugMode) {
                    println("[DEBUG] Released key code: $keyCode")
                }
                Thread.sleep(5) // Небольшая задержка между отпусканиями
            } catch (e: Exception) {
                println("⚠️ Ошибка при отпускании клавиши $keyCode: ${e.message}")
            }
        }
        pressedKeys.clear()

        // Отпускаем все кнопки мыши
        mouseToRelease.forEach { mask ->
            try {
                robot.mouseRelease(mask)
                if (debugMode) {
                    println("[DEBUG] Released mouse mask: $mask")
                }
                Thread.sleep(5)
            } catch (e: Exception) {
                println("⚠️ Ошибка при отпускании кнопки мыши $mask: ${e.message}")
            }
        }
        pressedMouseButtons.clear()

        println("✓ Состояние очищено")
    }
}