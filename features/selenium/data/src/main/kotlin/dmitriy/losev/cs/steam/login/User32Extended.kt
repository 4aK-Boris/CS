package dmitriy.losev.cs.steam.login

import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.win32.W32APIOptions

// Расширяем User32 для дополнительных функций
interface User32Extended : User32 {

    fun SendMessage(hWnd: WinDef.HWND, msg: Int, wParam: WinDef.WPARAM, lParam: String): WinDef.LRESULT

    override fun SendMessage(hWnd: WinDef.HWND, msg: Int, wParam: WinDef.WPARAM, lParam: WinDef.LPARAM): WinDef.LRESULT

    override fun GetClassName(hWnd: WinDef.HWND, lpClassName: CharArray, nMaxCount: Int): Int

    override fun GetWindowText(hWnd: WinDef.HWND, lpString: CharArray, nMaxCount: Int): Int

    override fun GetWindowRect(hWnd: WinDef.HWND, lpRect: WinDef.RECT): Boolean

    override fun SetCursorPos(x: Long, y: Long): Boolean


    fun mouseEvent(dwFlags: Int, dx: Int, dy: Int, dwData: Int, dwExtraInfo: Pointer?)

    companion object {

        // Mouse events
        const val MOUSEEVENTF_LEFTDOWN = 0x0002
        const val MOUSEEVENTF_LEFTUP = 0x0004
        const val MOUSEEVENTF_ABSOLUTE = 0x8000

        val instance: User32Extended = Native.load("user32", User32Extended::class.java, W32APIOptions.DEFAULT_OPTIONS) as User32Extended
    }
}