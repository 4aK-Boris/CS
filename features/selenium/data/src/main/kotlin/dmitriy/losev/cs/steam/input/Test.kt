package dmitriy.losev.cs.steam.input

import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import dmitriy.losev.cs.steam.ConsoleEncodingFix
import java.io.File
import kotlinx.coroutines.delay

/**
 * Тест захвата клавиш - раскомментируйте эту функцию для тестирования
 */
fun testKeyCapture() {
    ConsoleEncodingFix.fix()

    println("=".repeat(70))
    println("Тест захвата клавиш")
    println("=".repeat(70))
    println("Нажимайте клавиши для проверки их кодов")
    println("Особенно проверьте: TAB, ESC, F")
    println("Нажмите Ctrl+C для выхода")
    println("=".repeat(70))

    try {
        GlobalScreen.registerNativeHook()
    } catch (e: Exception) {
        println("Ошибка регистрации native hook: ${e.message}")
        return
    }

    GlobalScreen.addNativeKeyListener(object : NativeKeyListener {
        override fun nativeKeyPressed(e: NativeKeyEvent) {
            val keyText = NativeKeyEvent.getKeyText(e.keyCode)
            val mappedKey = KeyEvent.getKeyEvent(e)

            println("\n[PRESS] Key: $keyText")
            println("  Native code: ${e.keyCode}")
            println("  Mapped to: ${mappedKey?.name ?: "⚠️ NOT MAPPED"}")

            // Специальная проверка для ESC, TAB, F
            when {
                keyText == "Escape" || e.keyCode == NativeKeyEvent.VC_ESCAPE -> {
                    println("\n  ✅ ESC DETECTED!")
                    println("  Code matches: ${e.keyCode == NativeKeyEvent.VC_ESCAPE}")
                }
                keyText == "Tab" || e.keyCode == NativeKeyEvent.VC_TAB -> {
                    println("\n  ✅ TAB DETECTED!")
                }
                keyText == "F" || e.keyCode == NativeKeyEvent.VC_F -> {
                    println("\n  ✅ F DETECTED!")
                }
            }
        }

        override fun nativeKeyReleased(e: NativeKeyEvent) {
            val keyText = NativeKeyEvent.getKeyText(e.keyCode)
            val mappedKey = KeyEvent.getKeyEvent(e)
            println("[RELEASE] Key: $keyText (${mappedKey?.name ?: "NOT MAPPED"})")
        }

        override fun nativeKeyTyped(e: NativeKeyEvent) {}
    })

    // Держим программу запущенной
    Thread.currentThread().join()
}

// Main
suspend fun main() {

    // Раскомментируйте для теста захвата клавиш:
    // testKeyCapture()
    // return

    ConsoleEncodingFix.fix()

    println("""
    ╔══════════════════════════════════════════════════════════════════╗
    ║     CS2 High-Frequency Input Recorder (Kotlin)                   ║
    ║                                                                  ║
    ║  • Input на высокой частоте (256-1000 Hz)                        ║
    ║  • События от CS2 через GSI                                      ║
    ║  • Корутины для производительности                               ║
    ╚══════════════════════════════════════════════════════════════════╝
    """.trimIndent())

    // Создаём конфиг если нужно
    if (!File("gamestate_integration_events.cfg").exists()) {
        //createGSIConfig()
        println()
    }

    // Запускаем recorder
    val recorder = HighFrequencyInputRecorder(
        inputHz = 256,  // Можно менять
    )

    delay(5_000L)

    recorder.startRecording()

    delay(10_000L)

    recorder.stopRecording(gameId = 1, steamId = 1, roundNumber = 1)
}