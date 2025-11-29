package dmitriy.losev.cs.window

import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinUser
import com.sun.jna.ptr.IntByReference
import dmitriy.losev.cs.Context
import dmitriy.losev.cs.User32Extended
import dmitriy.losev.cs.process.ProcessHandler
import java.awt.Robot
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.awt.event.KeyEvent
import kotlinx.coroutines.delay
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Singleton
import kotlin.collections.forEach

@Singleton
class SteamWindowHandler(
    @Provided private val context: Context,
    private val user32Extended: User32Extended,
    private val processHandler: ProcessHandler
) {

    private val robot = Robot()

    private val loginAndPasswordAuthWindowDescriptors = mutableMapOf<String, WinDef.HWND>()
    private val steamGuardCodeAuthWindowDescriptors = mutableMapOf<String, WinDef.HWND>()

    suspend fun findSteamLoginAndPasswordAuthWindowDescriptor(key: String, steamProcessId: Long): Boolean {

        var windowDescriptor: WinDef.HWND?
        var count = 0

        do {
            windowDescriptor = findSteamWindowByPid(steamProcessId)
            count++
            delay(1000L)

        } while (windowDescriptor == null && count < context.steamConfig.waitingTimeForSteamLoginAndPasswordWindow)

        if (windowDescriptor != null) {
            loginAndPasswordAuthWindowDescriptors[key] = windowDescriptor
        }

        return windowDescriptor != null
    }

    suspend fun findSteamSteamGuardCodeAuthWindowDescriptor(key: String, steamProcessId: Long): Boolean {

        var windowDescriptor: WinDef.HWND?
        var count = 0

        do {
            windowDescriptor = findSteamWindowByPid(steamProcessId)
            count++
            delay(1000L)

        } while ((windowDescriptor == null || windowDescriptor == loginAndPasswordAuthWindowDescriptors[key]) && count < context.steamConfig.waitingTimeForSteamGuardCodeWindow)

        if (windowDescriptor != null) {
            steamGuardCodeAuthWindowDescriptors[key] = windowDescriptor
        }

        return windowDescriptor != null
    }

    suspend fun setWindowOnTop(key: String) {

        loginAndPasswordAuthWindowDescriptors[key]?.let { windowDescriptor ->

            val isActive = isWindowActive(windowDescriptor = windowDescriptor)

            if (isActive.not()) {

                user32Extended.ShowWindow(windowDescriptor, WinUser.SW_SHOW)

                delay(50L)

                user32Extended.SetForegroundWindow(windowDescriptor)

                delay(50L)

                user32Extended.SetActiveWindow(windowDescriptor)

                delay(500L)
            }
        }
    }

    fun isWindowActive(windowDescriptor: WinDef.HWND): Boolean {
        val foregroundWindow = user32Extended.GetForegroundWindow()
        return foregroundWindow != null && foregroundWindow.pointer == windowDescriptor.pointer
    }

    suspend fun closeSteamWindows(steamProcessId: Long) {

        val windowDescriptors = mutableListOf<WinDef.HWND>()
        var count = 0

        do {
            windowDescriptors.addAll(elements = getSteamWindowDescriptors(steamProcessId))
            count++
            delay(1_000L)
        } while (windowDescriptors.isEmpty() && count < context.steamConfig.waitingTimeForSteamWindows)

        windowDescriptors.forEach { windowDescriptor ->
            user32Extended.PostMessageA(
                windowDescriptor,
                User32Extended.WM_CLOSE,
                WinDef.WPARAM(0),
                WinDef.LPARAM(0)
            )
        }
    }

    suspend fun nextWindow() {

        pressKey(keyCode = KeyEvent.VK_TAB)

        delay(500L)
    }

    suspend fun pressSpace() {

        pressKey(keyCode = KeyEvent.VK_SPACE)

        delay(500L)
    }

    suspend fun pressEnter() {

        pressKey(keyCode = KeyEvent.VK_ENTER)

        delay(500L)
    }

    suspend fun deleteAllFromWindow() {

        robot.keyPress(KeyEvent.VK_CONTROL)
        robot.keyPress(KeyEvent.VK_A)

        robot.keyRelease(KeyEvent.VK_A)
        robot.keyRelease(KeyEvent.VK_CONTROL)

        delay(500L)

        pressKey(keyCode = KeyEvent.VK_DELETE)

        delay(500L)
    }

    suspend fun pasteTextInWindow(text: String) {

        val clipboard = Toolkit.getDefaultToolkit().systemClipboard
        clipboard.setContents(StringSelection(text), null)

        delay(500L)

        robot.keyPress(KeyEvent.VK_CONTROL)
        robot.keyPress(KeyEvent.VK_V)

        robot.keyRelease(KeyEvent.VK_V)
        robot.keyRelease(KeyEvent.VK_CONTROL)

        delay(500L)
    }

    /**
     * Нажатие клавиши через Robot
     */
    private suspend fun pressKey(keyCode: Int) {

        robot.keyPress(keyCode)

        delay(50L)

        robot.keyRelease(keyCode)

        delay(100L)
    }

    private fun findSteamWindowByPid(steamProcessId: Long): WinDef.HWND? {
        return getSteamWindowDescriptors(steamProcessId).firstOrNull()
    }

    private fun getSteamWindowDescriptors(steamProcessId: Long): List<WinDef.HWND> {
        return processHandler.getAllChildProcesses(parentProcessId = steamProcessId).mapNotNull {
            findWindowByProcessId(processId = steamProcessId)
        }
    }

    private fun findWindowByProcessId(processId: Long): WinDef.HWND? {

        var foundWindow: WinDef.HWND? = null

        user32Extended.EnumWindows({ windowDescriptor, _ ->

            if (user32Extended.IsWindowVisible(windowDescriptor)) {

                val pidRef = IntByReference()

                user32Extended.GetWindowThreadProcessId(windowDescriptor, pidRef)

                val windowPid = pidRef.value.toLong()

                if (windowPid == processId) {

                    val title = getWindowText(windowDescriptor)

                    if (title.isNotEmpty()) {
                        foundWindow = windowDescriptor
                        return@EnumWindows false
                    }
                }
            }

            true
        }, null)

        return foundWindow
    }

    private fun findWindowsByProcessId(processId: Long): List<WinDef.HWND> {

        val windowDescriptors = mutableListOf<WinDef.HWND>()

        user32Extended.EnumWindows({ hwnd, _ ->

            if (user32Extended.IsWindowVisible(hwnd)) {

                val pidRef = IntByReference()

                user32Extended.GetWindowThreadProcessId(hwnd, pidRef)

                val windowPid = pidRef.value.toLong()

                if (windowPid == processId) {

                    val title = getWindowText(hwnd)

                    println(title)

                    if (title.isNotEmpty()) {
                        windowDescriptors.add(hwnd)
                    }
                }
            }

            true
        }, null)

        return windowDescriptors
    }


    private fun getWindowText(hwnd: WinDef.HWND): String {
        val buffer = CharArray(size = 512)
        user32Extended.GetWindowText(hwnd, buffer, buffer.size)
        return String(chars = buffer).trim('\u0000')
    }
}