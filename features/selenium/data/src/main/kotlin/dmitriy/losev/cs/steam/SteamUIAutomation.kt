package dmitriy.losev.cs.steam

import com.sun.jna.Pointer
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef.HWND
import java.awt.Robot
import java.awt.event.KeyEvent
import mu.KotlinLogging

/**
 * Автоматизация ввода в Steam через UI Automation
 */
class SteamUIAutomation {

    private val logger = KotlinLogging.logger {}

    private val user32 = User32.INSTANCE
    private val robot = Robot()

    /**
     * Находит окно Steam по заголовку
     */
    fun findSteamWindow(timeout: Long = 30000): HWND? {
        logger.info { "Поиск окна Steam (таймаут: ${timeout}ms)..." }

        val startTime = System.currentTimeMillis()

        while (System.currentTimeMillis() - startTime < timeout) {
            // Ищем главное окно Steam
            val hwnd = user32.FindWindow(null, "Steam")

            if (hwnd != null && !hwnd.pointer.equals(Pointer.NULL)) {
                logger.info { "✓ Окно Steam найдено: $hwnd" }
                return hwnd
            }

            // Также ищем окно логина
            val loginHwnd = user32.FindWindow(null, "Steam - Sign in")
            if (loginHwnd != null && !loginHwnd.pointer.equals(Pointer.NULL)) {
                logger.info { "✓ Окно логина Steam найдено: $loginHwnd" }
                return loginHwnd
            }

            Thread.sleep(500)
        }

        logger.warn { "⚠ Окно Steam не найдено" }
        return null
    }

    /**
     * Активирует окно и вводит текст
     */
    fun typeInWindow(hwnd: HWND, text: String, delay: Long = 50) {
        logger.debug { "Ввод текста в окно: $hwnd" }

        // Активируем окно
        user32.SetForegroundWindow(hwnd)
        Thread.sleep(200)

        // Вводим текст посимвольно
        text.forEach { char ->
            typeChar(char)
            Thread.sleep(delay)
        }
    }

    /**
     * Вводит один символ
     */
    private fun typeChar(char: Char) {
        when {
            char.isLetterOrDigit() -> {
                val keyCode = when {
                    char.isDigit() -> KeyEvent.VK_0 + (char - '0')
                    char.isUpperCase() -> {
                        robot.keyPress(KeyEvent.VK_SHIFT)
                        val code = KeyEvent.VK_A + (char.uppercaseChar() - 'A')
                        robot.keyPress(code)
                        robot.keyRelease(code)
                        robot.keyRelease(KeyEvent.VK_SHIFT)
                        return
                    }
                    else -> KeyEvent.VK_A + (char.uppercaseChar() - 'A')
                }

                robot.keyPress(keyCode)
                robot.keyRelease(keyCode)
            }
            char == '@' -> {
                robot.keyPress(KeyEvent.VK_SHIFT)
                robot.keyPress(KeyEvent.VK_2)
                robot.keyRelease(KeyEvent.VK_2)
                robot.keyRelease(KeyEvent.VK_SHIFT)
            }
            char == '_' -> {
                robot.keyPress(KeyEvent.VK_SHIFT)
                robot.keyPress(KeyEvent.VK_MINUS)
                robot.keyRelease(KeyEvent.VK_MINUS)
                robot.keyRelease(KeyEvent.VK_SHIFT)
            }
            // Добавьте другие специальные символы по необходимости
        }
    }

    /**
     * Нажимает Tab
     */
    fun pressTab() {
        robot.keyPress(KeyEvent.VK_TAB)
        robot.keyRelease(KeyEvent.VK_TAB)
    }

    /**
     * Нажимает Enter
     */
    fun pressEnter() {
        robot.keyPress(KeyEvent.VK_ENTER)
        robot.keyRelease(KeyEvent.VK_ENTER)
    }

    /**
     * Автоматический логин в Steam
     */
    fun autoLogin(hwnd: HWND, username: String, password: String, waitForCode: Boolean = true): Boolean {
        logger.info { "Автоматический логин: $username" }

        try {
            // Ждём загрузки окна
            Thread.sleep(2000)

            // Вводим username
            logger.debug { "Ввод username..." }
            typeInWindow(hwnd, username)
            Thread.sleep(500)

            // Tab к полю пароля
            pressTab()
            Thread.sleep(300)

            // Вводим password
            logger.debug { "Ввод password..." }
            typeInWindow(hwnd, password)
            Thread.sleep(500)

            // Enter для входа
            pressEnter()

            logger.info { "✓ Данные введены, ожидание авторизации..." }

            // Если нужен код - ждём
            if (waitForCode) {
                logger.info { "⏳ Ожидание ввода Steam Guard кода..." }
                // Здесь можно либо ждать ручного ввода, либо автоматизировать
                Thread.sleep(60000) // 60 секунд на ввод кода
            }

            return true

        } catch (e: Exception) {
            logger.error(e) { "Ошибка автологина" }
            return false
        }
    }
}