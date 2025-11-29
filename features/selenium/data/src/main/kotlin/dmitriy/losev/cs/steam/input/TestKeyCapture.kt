package dmitriy.losev.cs.steam.input

import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import dmitriy.losev.cs.steam.ConsoleEncodingFix
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * Утилита для тестирования захвата клавиш
 * Запустите и нажимайте клавиши, чтобы увидеть, какие коды они генерируют
 */
fun main(): Unit = runBlocking {

    ConsoleEncodingFix.fix()

    val inputPlayback = InputPlayback()

    inputPlayback.loadTicks(filePath = "feature/selenium/data/src/main/resources/game - 1, steamId - 1, round - 1.csv")

    delay(5_000L)

    inputPlayback.play()
}