package dmitriy.losev.cs

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("MobileProxy")

private val devicePorts = mapOf(
    "f3fdb2e0" to 1070
)

fun main(args: Array<String>) {

    devicePorts.forEach { (deviceId, port) ->

        runAdb("forward", "tcp:$port", "tcp:$port")

        logger.info("Proxy $deviceId: localhost:$port")
    }

    logger.info("Rotation: GET /rotate?device_id=<device_id>")

    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    install(ContentNegotiation) {
        json(
            json = Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }
        )
    }

    routing {
        get(path = "/rotate") {
            val deviceId = call.request.queryParameters["device_id"]

            if (deviceId == null) {
                call.respond(HttpStatusCode.BadRequest, RotateResponse(success = false, error = "device_id parameter is required"))
                return@get
            }

            val result = rotateIp(deviceId)
            if (result.success) {
                call.respond(result)
            } else {
                call.respond(HttpStatusCode.InternalServerError, result)
            }
        }

        get(path = "/health") {
            call.respond(HealthResponse(status = "ok"))
        }
    }
}

@Serializable
data class RotateResponse(
    val success: Boolean,
    val error: String? = null
)

@Serializable
data class HealthResponse(
    val status: String
)

private suspend fun rotateIp(deviceId: String): RotateResponse {
    return try {
        logger.info("Rotating IP for device $deviceId...")
        runAdb("-s", deviceId, "shell", "cmd", "connectivity", "airplane-mode", "enable")
        delay(2000)
        runAdb("-s", deviceId, "shell", "cmd", "connectivity", "airplane-mode", "disable")
        delay(5000)

        val port = devicePorts[deviceId]
        if (port != null) {
            runAdb("forward", "tcp:$port", "tcp:$port")
        }

        logger.info("IP rotated successfully for device $deviceId")
        RotateResponse(success = true)
    } catch (e: Exception) {
        logger.error("Failed to rotate IP for device $deviceId: ${e.message}")
        RotateResponse(success = false, error = e.message)
    }
}

private fun runAdb(vararg args: String): String {
    val process = ProcessBuilder("adb", *args)
        .redirectErrorStream(true)
        .start()
    val output = process.inputStream.bufferedReader().readText()
    val exitCode = process.waitFor()
    if (exitCode != 0) {
        throw RuntimeException("ADB command failed: adb ${args.joinToString(" ")}, output: $output")
    }
    return output
}