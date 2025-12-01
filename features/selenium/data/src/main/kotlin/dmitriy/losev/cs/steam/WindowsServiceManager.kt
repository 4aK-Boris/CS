package dmitriy.losev.cs.steam

// WindowsServiceManager.kt
import com.sun.jna.platform.win32.Advapi32
import com.sun.jna.platform.win32.Kernel32
import com.sun.jna.platform.win32.WinNT
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class WindowsServiceManager {

    companion object {
        const val STEAM_SERVICE_NAME = "Steam Client Service"
    }

    /**
     * Проверяет, запущена ли программа с правами администратора
     */
    fun isRunningAsAdmin(): Boolean {
        return try {
            val processHandle = Kernel32.INSTANCE.GetCurrentProcess()
            val token = WinNT.HANDLEByReference()

            Advapi32.INSTANCE.OpenProcessToken(
                processHandle,
                WinNT.TOKEN_QUERY,
                token
            )

            val elevation = WinNT.TOKEN_ELEVATION()
            val size = com.sun.jna.ptr.IntByReference(elevation.size())

            Advapi32.INSTANCE.GetTokenInformation(
                token.value,
                WinNT.TOKEN_INFORMATION_CLASS.TokenElevation,
                elevation,
                elevation.size(),
                size
            )

            elevation.TokenIsElevated != 0
        } catch (e: Exception) {
            logger.error(e) { "Ошибка проверки прав администратора" }
            false
        }
    }

    /**
     * Останавливает Steam Client Service
     */
    fun stopSteamService(): Boolean {
        logger.info { "Останавка Steam Client Service..." }

        return try {
            val process = Runtime.getRuntime().exec("sc stop \"$STEAM_SERVICE_NAME\"")
            process.waitFor()
            Thread.sleep(2000) // Даём время на остановку
            true
        } catch (e: Exception) {
            logger.error(e) { "Ошибка остановки службы" }
            false
        }
    }

    /**
     * Запускает Steam Client Service
     */
    fun startSteamService(): Boolean {
        logger.info { "Запуск Steam Client Service..." }

        return try {
            val process = Runtime.getRuntime().exec("sc start \"$STEAM_SERVICE_NAME\"")
            process.waitFor()
            Thread.sleep(2000)
            true
        } catch (e: Exception) {
            logger.error(e) { "Ошибка запуска службы" }
            false
        }
    }
}