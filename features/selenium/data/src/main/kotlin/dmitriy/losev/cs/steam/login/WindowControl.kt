package dmitriy.losev.cs.steam.login

import com.sun.jna.platform.win32.WinDef

// Класс для хранения информации о контроле
data class WindowControl(
    val hwnd: WinDef.HWND,
    val className: String,
    val text: String
)
