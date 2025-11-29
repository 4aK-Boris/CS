package dmitriy.losev.cs.steam

import com.sun.jna.platform.win32.Advapi32Util
import com.sun.jna.platform.win32.WinNT

fun isRunningAsAdmin(): Boolean {
    return try {
        Advapi32Util.isCurrentProcessElevated()
    } catch (e: Exception) {
        false
    }
}