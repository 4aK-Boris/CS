package dmitriy.losev.cs.steam

import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.platform.win32.*
import com.sun.jna.ptr.IntByReference
import com.sun.jna.win32.W32APIOptions
import mu.KotlinLogging

/**
 * Расширенный интерфейс NtDll для работы с handles
 */
interface NtDll : com.sun.jna.platform.win32.NtDll {
    companion object {
        val INSTANCE: NtDll = Native.load("ntdll", NtDll::class.java, W32APIOptions.DEFAULT_OPTIONS)
    }

    fun NtQuerySystemInformation(
        SystemInformationClass: Int,
        SystemInformation: Pointer?,
        SystemInformationLength: Int,
        ReturnLength: IntByReference?
    ): Int

    fun NtQueryObject(
        Handle: WinNT.HANDLE?,
        ObjectInformationClass: Int,
        ObjectInformation: Pointer?,
        ObjectInformationLength: Int,
        ReturnLength: IntByReference?
    ): Int
}

