package dmitriy.losev.cs.steam

import mu.KotlinLogging

private val serviceLogger = KotlinLogging.logger("SteamServiceManager")

class SteamServiceManager {

    private val serviceName = "Steam Client Service"

    fun stopSteamService() {
        try {
            val result = ProcessBuilder("sc", "stop", "\"$serviceName\"")
                .redirectOutput(ProcessBuilder.Redirect.PIPE)
                .redirectError(ProcessBuilder.Redirect.PIPE)
                .start()
                .waitFor()

            if (result == 0) {
                serviceLogger.info { "✓ Steam Client Service остановлен" }
            } else {
                serviceLogger.warn { "⚠ Не удалось остановить службу (возможно, уже остановлена)" }
            }
        } catch (e: Exception) {
            serviceLogger.warn { "⚠ Ошибка при остановке службы: ${e.message}" }
        }
    }

    fun startSteamService() {
        try {
            val result = ProcessBuilder("sc", "start", "\"$serviceName\"")
                .redirectOutput(ProcessBuilder.Redirect.PIPE)
                .redirectError(ProcessBuilder.Redirect.PIPE)
                .start()
                .waitFor()

            if (result == 0) {
                serviceLogger.info { "✓ Steam Client Service запущен" }
            } else {
                serviceLogger.warn { "⚠ Не удалось запустить службу" }
            }
        } catch (e: Exception) {
            serviceLogger.warn { "⚠ Ошибка при запуске службы: ${e.message}" }
        }
    }
}