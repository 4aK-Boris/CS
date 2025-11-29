package dmitriy.losev.cs

import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef.HWND
import com.sun.jna.platform.win32.WinDef.WPARAM
import com.sun.jna.platform.win32.WinDef.LPARAM
import com.sun.jna.platform.win32.WinDef.LRESULT
import com.sun.jna.platform.win32.WinDef.RECT

interface User32Extended : User32 {

    override fun SendMessage(hWnd: HWND, msg: Int, wParam: WPARAM?, lParam: LPARAM?): LRESULT

    override fun GetClassName(hWnd: HWND, lpClassName: CharArray, nMaxCount: Int): Int

    override fun GetWindowText(hWnd: HWND, lpString: CharArray, nMaxCount: Int): Int

    override fun GetWindowRect(hWnd: HWND?, lpRect: RECT): Boolean

    override fun SetCursorPos(x: Long, y: Long): Boolean

    override fun SetForegroundWindow(hWnd: HWND?): Boolean

    fun FindWindowExA(hwndParent: HWND?, hwndChildAfter: HWND?, lpszClass: String?, lpszWindow: String?): HWND?

    fun SendMessageA(hWnd: HWND?, Msg: Int, wParam: WPARAM?, lParam: LPARAM?): LRESULT?

    fun SendMessageW(hWnd: HWND?, Msg: Int, wParam: WPARAM?, lParam: String?): LRESULT?

    fun PostMessageA(hWnd: HWND?, Msg: Int, wParam: WPARAM?, lParam: LPARAM?): Boolean

    fun SetActiveWindow(hWnd: HWND?): HWND?

    override fun IsWindowEnabled(hWnd: HWND?): Boolean

    fun GetWindowTextA(hWnd: HWND?, lpString: ByteArray?, nMaxCount: Int): Int

    fun GetClassNameA(hWnd: HWND?, lpClassName: ByteArray?, nMaxCount: Int): Int

    fun GetFocus(): HWND?

    companion object {

        const val WM_SETTEXT = 0x000C

        const val WM_CLOSE = 0x0010
        const val WM_CHAR = 0x0102
        const val WM_KEYDOWN = 0x0100
        const val WM_KEYUP = 0x0101

        const val SW_MINIMIZE = 6
    }
}