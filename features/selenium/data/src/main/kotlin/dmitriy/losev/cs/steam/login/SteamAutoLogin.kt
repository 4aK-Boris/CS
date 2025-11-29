package dmitriy.losev.cs.steam.login

import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinDef.WPARAM
import com.sun.jna.platform.win32.WinDef.LPARAM
import com.sun.jna.platform.win32.WinDef.HWND
import java.awt.Robot
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.awt.event.KeyEvent
import kotlinx.coroutines.delay

class SteamAutoLogin {

    private val user32 = User32Extended.instance
    private val robot = Robot()

    init {
        // Уменьшаем задержку между событиями Robot
        robot.autoDelay = 50
    }

    /**
     * Найти окно по части заголовка
     */
    fun findWindowByTitle(titlePart: String): HWND? {
        var foundWindow: HWND? = null

        user32.EnumWindows({ hwnd, _ ->
            if (user32.IsWindowVisible(hwnd)) {
                val title = getWindowText(hwnd)
                if (title.contains(titlePart, ignoreCase = true)) {
                    foundWindow = hwnd
                    return@EnumWindows false
                }
            }
            true
        }, null)

        return foundWindow
    }

    private fun getWindowText(hwnd: HWND): String {
        val buffer = CharArray(512)
        user32.GetWindowText(hwnd, buffer, buffer.size)
        return String(buffer).trim('\u0000')
    }

    private fun getClassName(hwnd: HWND): String {
        val buffer = CharArray(256)
        user32.GetClassName(hwnd, buffer, buffer.size)
        return String(buffer).trim('\u0000')
    }

    fun findChildWindows(parentHwnd: HWND): List<WindowControl> {
        val children = mutableListOf<WindowControl>()

        user32.EnumChildWindows(parentHwnd, { hwnd, _ ->
            val className = getClassName(hwnd)
            val text = getWindowText(hwnd)
            children.add(WindowControl(hwnd, className, text))
            true
        }, null)

        return children
    }

    /**
     * Ввод текста через Robot API (работает с CEF!)
     */
    private fun typeTextWithRobot(text: String) {
        text.forEach { char ->
            when {
                char.isLetterOrDigit() -> {
                    val keyCode = when {
                        char.isUpperCase() -> {
                            robot.keyPress(KeyEvent.VK_SHIFT)
                            KeyEvent.getExtendedKeyCodeForChar(char.lowercaseChar().code)
                        }
                        else -> KeyEvent.getExtendedKeyCodeForChar(char.code)
                    }

                    if (keyCode != KeyEvent.VK_UNDEFINED) {
                        robot.keyPress(keyCode)
                        robot.keyRelease(keyCode)
                    }

                    if (char.isUpperCase()) {
                        robot.keyRelease(KeyEvent.VK_SHIFT)
                    }
                }
                char == '@' -> {
                    robot.keyPress(KeyEvent.VK_SHIFT)
                    robot.keyPress(KeyEvent.VK_2)
                    robot.keyRelease(KeyEvent.VK_2)
                    robot.keyRelease(KeyEvent.VK_SHIFT)
                }
                char == '.' -> {
                    robot.keyPress(KeyEvent.VK_PERIOD)
                    robot.keyRelease(KeyEvent.VK_PERIOD)
                }
                char == '_' -> {
                    robot.keyPress(KeyEvent.VK_SHIFT)
                    robot.keyPress(KeyEvent.VK_MINUS)
                    robot.keyRelease(KeyEvent.VK_MINUS)
                    robot.keyRelease(KeyEvent.VK_SHIFT)
                }
                char == '-' -> {
                    robot.keyPress(KeyEvent.VK_MINUS)
                    robot.keyRelease(KeyEvent.VK_MINUS)
                }
                char.isDigit() -> {
                    val keyCode = KeyEvent.VK_0 + char.digitToInt()
                    robot.keyPress(keyCode)
                    robot.keyRelease(keyCode)
                }
                else -> {
                    // Пытаемся ввести через extended key code
                    val keyCode = KeyEvent.getExtendedKeyCodeForChar(char.code)
                    if (keyCode != KeyEvent.VK_UNDEFINED) {
                        robot.keyPress(keyCode)
                        robot.keyRelease(keyCode)
                    }
                }
            }
            Thread.sleep(30) // Задержка между символами
        }
    }

    /**
     * Вставка текста через буфер обмена (САМЫЙ НАДЁЖНЫЙ для CEF!)
     */
    private fun pasteText(text: String) {
        // Копируем в буфер обмена
        val clipboard = Toolkit.getDefaultToolkit().systemClipboard
        clipboard.setContents(StringSelection(text), null)
        Thread.sleep(100)

        // Вставляем через Ctrl+V
        robot.keyPress(KeyEvent.VK_CONTROL)
        robot.keyPress(KeyEvent.VK_V)
        robot.keyRelease(KeyEvent.VK_V)
        robot.keyRelease(KeyEvent.VK_CONTROL)
        Thread.sleep(100)
    }

    /**
     * Нажатие клавиши через Robot
     */
    private fun pressKey(keyCode: Int) {
        robot.keyPress(keyCode)
        Thread.sleep(50)
        robot.keyRelease(keyCode)
        Thread.sleep(100)
    }

    /**
     * Главная функция авторизации (для CEF-based Steam)
     * @param username Имя пользователя
     * @param password Пароль
     * @param twoFactorCode Steam Guard код (опционально)
     * @param toggleRememberMe Нужно ли включать галку "Запомнить меня"?
     *        - true: нажмёт Space для переключения галки
     *        - false: не трогает галку (используй если она уже включена)
     */
    fun login(
        username: String,
        password: String,
        twoFactorCode: String? = null,
        toggleRememberMe: Boolean = false  // По умолчанию не трогаем
    ): Boolean {
        println("🔍 Ищем окно Steam...")

        var steamWindow: HWND? = null
        repeat(30) {
            steamWindow = findWindowByTitle("Steam")
            if (steamWindow != null) {
                println("✅ Окно найдено: ${steamWindow.pointer}")
                return@repeat
            }
            Thread.sleep(1000)
        }

        if (steamWindow == null) {
            println("❌ Окно Steam не найдено!")
            return false
        }

        // Активируем окно
        user32.SetForegroundWindow(steamWindow)
        Thread.sleep(500)

        // Анализируем контролы для диагностики
        println("🔍 Анализируем контролы окна...")
        val children = findChildWindows(steamWindow)

        println("   Найдено ${children.size} контролов:")
        children.forEachIndexed { index, control ->
            if (control.className.isNotEmpty() || control.text.isNotEmpty()) {
                println("   [$index] Class: ${control.className.padEnd(25)} Text: ${control.text}")
            }
        }

        // Steam использует CEF - обычные контролы не работают!
        println("\n⚠️  Steam использует Chromium (CEF) интерфейс!")
        println("   Используем Clipboard + Robot API для ввода...\n")

        // ВАЖНО: Переходим к полю логина через Shift+Tab
        // По умолчанию фокус часто на поле пароля или кнопке


        println("⌨️  Навигация к полю логина (Shift+Tab)...")
        repeat(20) {
            pressKey(KeyEvent.VK_DELETE)
            Thread.sleep(100)
        }

        Thread.sleep(200)

        // Вводим username через буфер обмена (НАДЁЖНЕЕ!)
        println("⌨️  Вводим username: $username")
        pasteText(username)
        Thread.sleep(300)

        // Tab к полю пароля
        println("⌨️  Tab к полю пароля...")
        pressKey(KeyEvent.VK_TAB)
        Thread.sleep(300)

        repeat(20) {
            pressKey(KeyEvent.VK_DELETE)
            Thread.sleep(100)
        }

        Thread.sleep(100)

        // Вводим password через буфер обмена
        println("⌨️  Вводим password: ${"*".repeat(password.length)}")
        pasteText(password)
        Thread.sleep(300)


        pressKey(KeyEvent.VK_TAB)
        Thread.sleep(300)

        pressKey(KeyEvent.VK_SPACE)

        Thread.sleep(300)

        pressKey(KeyEvent.VK_TAB)

        // Нажимаем Enter для входа
        println("⌨️  Нажимаем Enter для входа...")
        pressKey(KeyEvent.VK_ENTER)

        // Обработка Steam Guard
        if (twoFactorCode != null) {
            println("\n⏳ Ждём окно Steam Guard (10 секунд)...")
            Thread.sleep(10000)

            var guardWindow = findWindowByTitle("Steam Guard")

            // Если окно не нашлось, возможно код нужен в основном окне
            if (guardWindow == null) {
                println("⚠️  Отдельное окно Steam Guard не найдено")
                println("   Пробуем ввести код в текущее окно...")
                guardWindow = steamWindow
            } else {
                println("✅ Окно Steam Guard найдено!")
                user32.SetForegroundWindow(guardWindow)
                Thread.sleep(500)
            }

            // Вводим код через буфер обмена
            println("⌨️  Вводим 2FA код: $twoFactorCode")
            pasteText(twoFactorCode)
            Thread.sleep(300)

            // Enter для подтверждения
            println("⌨️  Нажимаем Enter...")
            pressKey(KeyEvent.VK_ENTER)
        }

        println("\n✅ Автоматизация завершена!")
        println("   Проверьте Steam - авторизация должна пройти.")
        return true
    }
}
