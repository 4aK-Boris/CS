package dmitriy.losev.cs.steam.input

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import dmitriy.losev.cs.steam.input.data.JsonCSData
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.request.receive
import io.ktor.server.response.respondText
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import kotlin.random.Random

class Server {

    init {
        // Отключаем логирование ошибок сети в Netty
        (LoggerFactory.getLogger("io.netty") as? Logger)?.level = Level.ERROR
        (LoggerFactory.getLogger("io.ktor") as? Logger)?.level = Level.WARN
    }

    private val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

    private var phase: String? = null

    private val highFrequencyInputRecorder = HighFrequencyInputRecorder(inputHz = 128)

    private val gameId = Random.nextInt()

    private var inputPlayback: InputPlayback? = null
    private var playbackJob: Job? = null
    private val playbackScope = CoroutineScope(Dispatchers.Default)

    fun startRecording() {
        embeddedServer(Netty, port = 8085) {
            routing {
                post("/test") {
                    val gsiData = call.receive<String>()
                    val jsonData = json.decodeFromString<JsonCSData>(string = gsiData)

                    when(jsonData.round?.phase) {
                        "freezetime" -> {
                            if (phase != "freezetime") {
                                phase = "freezetime"
                                println("phase = freezetime")
                            }
                        }
                        "live" -> {
                            if (phase != "live") {
                                phase = "live"
                                println("phase = live")
                                highFrequencyInputRecorder.startRecording()
                            }
                        }
                        "over" -> {
                            if (phase != "over") {
                                phase = "over"
                                println("phase = over")
                                highFrequencyInputRecorder.stopRecording(
                                    gameId = gameId,
                                    roundNumber = jsonData.map?.round ?: 0,
                                    steamId = jsonData.provider.steamId
                                )
                            }
                        }
                        else -> {
                            if (phase != null) {
                                phase = null
                                println("phase = null")
                            }
                        }
                    }

                    call.respondText("OK")
                }
            }
        }.start(wait = true)
    }

    fun startPlaying() {
        embeddedServer(Netty, port = 8085) {
            routing {
                post("/test") {
                    val gsiData = call.receive<String>()
                    val jsonData = json.decodeFromString<JsonCSData>(string = gsiData)

                    when(jsonData.round?.phase) {
                        "freezetime" -> {
                            if (phase != "freezetime") {
                                phase = "freezetime"
                                println("phase = freezetime")
                                val round = if ((jsonData.map?.round ?: 1) == 0) 1 else jsonData.map?.round ?: 1
                                println("Раунд: $round")

                                // Останавливаем предыдущее воспроизведение если есть
                                inputPlayback?.stop()
                                playbackJob?.cancel()

                                // Даём время на очистку
                                Thread.sleep(100)

                                // Создаем новый экземпляр для каждого раунда
                                inputPlayback = InputPlayback(debugMode = false) // Включите true для отладки

                                try {
                                    inputPlayback?.loadTicks(filePath = "feature/selenium/data/src/main/resources/game - -796445265, steamId - 76561198303848981, round - $round.csv")
                                    println("✓ Файл раунда $round загружен")
                                } catch (e: Exception) {
                                    println("⚠️ Ошибка загрузки файла для раунда $round: ${e.message}")
                                }
                            }
                        }
                        "live" -> {
                            if (phase != "live") {
                                phase = "live"
                                println("phase = live")
                                playbackJob = playbackScope.launch {
                                    inputPlayback?.play(delaySeconds = 0)
                                }
                            }
                        }
                        "over" -> {
                            if (phase != "over") {
                                phase = "over"
                                println("phase = over")
                                inputPlayback?.stop()
                            }
                        }
                        else -> {
                            if (phase != null) {
                                phase = null
                                println("phase = null")
                            }
                        }
                    }

                    call.respondText("OK")
                }
            }
        }.start(wait = true)
    }
}