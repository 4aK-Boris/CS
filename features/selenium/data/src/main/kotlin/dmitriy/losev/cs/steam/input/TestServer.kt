package dmitriy.losev.cs.steam.input

import dmitriy.losev.cs.steam.ConsoleEncodingFix
import kotlinx.coroutines.runBlocking


fun main(): Unit = runBlocking {
    val server = Server()

    ConsoleEncodingFix.fix()

    server.startPlaying()

//    val inputPlayback = InputPlayback(debugMode = true)
//
//    inputPlayback.loadTicks("feature/selenium/data/src/main/resources/game - -1367964609, steamId - 76561198303848981, round - 1.csv")
//
//    inputPlayback.play(delaySeconds = 5)

}