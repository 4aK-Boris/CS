package dmitriy.losev.cs.mutex

import com.sun.jna.Library
import com.sun.jna.Pointer
import com.sun.jna.platform.win32.WinNT
import com.sun.jna.ptr.IntByReference

interface NtDll : Library {

    fun NtQuerySystemInformation(
        systemInformationClass: Int,
        systemInformation: Pointer,
        systemInformationLength: Int,
        returnLength: IntByReference
    ): Int

    fun NtQueryObject(
        objectHandle: WinNT.HANDLE?,
        objectInformationClass: Int,
        objectInformation: Pointer,
        objectInformationLength: Int,
        returnLength: IntByReference
    ): Int
}