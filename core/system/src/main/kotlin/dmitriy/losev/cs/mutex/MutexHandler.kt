package dmitriy.losev.cs.mutex

import com.sun.jna.Memory
import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.Structure
import com.sun.jna.platform.win32.Kernel32
import com.sun.jna.platform.win32.WinNT
import com.sun.jna.ptr.IntByReference
import org.koin.core.annotation.Singleton

@Singleton
class MutexHandler(
    private val ntDll: NtDll,
    private val kernel32: Kernel32
) {

    fun killMutexesInProcess(processId: Int, mutexFilter: String? = null): Int {

        val processHandle = kernel32.OpenProcess(
            WinNT.PROCESS_DUP_HANDLE or WinNT.PROCESS_QUERY_INFORMATION,
            false,
            processId
        ) ?: run {
            return 0
        }

        return try {

            val handles = getProcessHandles(processId)

            var killedCount = 0

            for (handleInfo in handles) {

                val handleValue = WinNT.HANDLE(Pointer((handleInfo.handle.toInt() and 0xFFFF).toLong()))

                val typeName = getHandleType(processHandle, handleValue)

                if (typeName != "Mutant") continue

                val objectName = getHandleName(processHandle, handleValue)

                if (objectName.isNullOrEmpty()) continue

                if (mutexFilter != null && !objectName.contains(other = mutexFilter, ignoreCase = true)) {
                    continue
                }

                if (isSingletonMutex(objectName)) {
                    if (closeRemoteHandle(processHandle = processHandle, handleToClose = handleValue)) killedCount++
                }
            }

            killedCount

        } finally {
            kernel32.CloseHandle(processHandle)
        }
    }

    private fun closeRemoteHandle(processHandle: WinNT.HANDLE, handleToClose: WinNT.HANDLE): Boolean {

        val targetHandleRef = WinNT.HANDLEByReference()
        val currentProcess = kernel32.GetCurrentProcess()

        val success = kernel32.DuplicateHandle(
            processHandle,
            handleToClose,
            currentProcess,
            targetHandleRef,
            0,
            false,
            DUPLICATE_CLOSE_SOURCE
        )

        if (success && targetHandleRef.value != null) {
            kernel32.CloseHandle(targetHandleRef.value)
        }

        return success
    }

    private fun getProcessHandles(processId: Int): List<SystemHandleInformation> {

        val result = mutableListOf<SystemHandleInformation>()

        var bufferSize = 0x10000

        var buffer = Memory(bufferSize.toLong())

        val returnLength = IntByReference()

        while (true) {

            val status = ntDll.NtQuerySystemInformation(
                systemInformationClass = SYSTEM_HANDLE_INFORMATION_CLASS,
                systemInformation = buffer,
                systemInformationLength = bufferSize,
                returnLength = returnLength
            )

            if (status == STATUS_INFO_LENGTH_MISMATCH) {

                bufferSize = returnLength.value

                buffer = Memory(bufferSize.toLong())

                continue
            }

            if (status != 0) break

            val handleCount = buffer.getInt(0)

            var offset = Native.POINTER_SIZE.toLong()

            repeat(times = handleCount) {

                val handlePointer = buffer.share(offset)

                val handleInfo = SystemHandleInformation(handlePointer)

                handleInfo.read()

                if (handleInfo.processId == processId) {
                    result.add(handleInfo)
                }

                offset += handleInfo.size().toLong()
            }

            break
        }

        return result
    }

    private fun getHandleType(processHandle: WinNT.HANDLE, handle: WinNT.HANDLE): String? {

        val duplicatedHandleRef = WinNT.HANDLEByReference()
        val currentProcess = kernel32.GetCurrentProcess()

        if (kernel32.DuplicateHandle(
                processHandle,
                handle,
                currentProcess,
                duplicatedHandleRef,
                0,
                false,
                DUPLICATE_SAME_ACCESS
            ).not()
        ) {
            return null
        }

        return try {

            val buffer = Memory(0x1000)

            val returnLength = IntByReference()

            val status = ntDll.NtQueryObject(
                objectHandle = duplicatedHandleRef.value,
                objectInformationClass = OBJECT_TYPE_INFORMATION,
                objectInformation = buffer,
                objectInformationLength = 0x1000,
                returnLength = returnLength
            )

            if (status != 0) {
                return null
            }

            try {
                val unicodeString = UnicodeString(buffer)
                unicodeString.getString()
            } catch (_: Exception) {
                null
            }

        } finally {
            kernel32.CloseHandle(duplicatedHandleRef.value)
        }
    }

    private fun getHandleName(processHandle: WinNT.HANDLE, handle: WinNT.HANDLE): String? {

        val duplicatedHandleRef = WinNT.HANDLEByReference()
        val currentProcess = kernel32.GetCurrentProcess()

        if (
            kernel32.DuplicateHandle(
                processHandle,
                handle,
                currentProcess,
                duplicatedHandleRef,
                0,
                false,
                DUPLICATE_SAME_ACCESS
            ).not()
        ) {
            return null
        }

        return try {

            val buffer = Memory(0x1000)
            val returnLength = IntByReference()

            if (
                ntDll.NtQueryObject(
                    objectHandle = duplicatedHandleRef.value,
                    objectInformationClass = OBJECT_NAME_INFORMATION,
                    objectInformation = buffer,
                    objectInformationLength = 0x1000,
                    returnLength = returnLength
                ) == 0
            ) {

                val unicodeString = UnicodeString(buffer)
                unicodeString.getString()

            } else null
        } finally {
            kernel32.CloseHandle(duplicatedHandleRef.value)
        }
    }

    private fun isSingletonMutex(name: String): Boolean {
        val patterns = listOf(
            "SingleInstance",
            "Singleton",
            "OnlyOne",
            "CS2_Mutex",
            "Source2_Mutex",
            "Steam_Singleton"
        )
        return patterns.any { pattern -> name.contains(other = pattern, ignoreCase = true) }
    }

    companion object {

        private const val SYSTEM_HANDLE_INFORMATION_CLASS = 16
        private const val OBJECT_NAME_INFORMATION = 1
        private const val OBJECT_TYPE_INFORMATION = 2
        private const val DUPLICATE_CLOSE_SOURCE = 0x00000001
        private const val DUPLICATE_SAME_ACCESS = 0x00000002
        private const val STATUS_INFO_LENGTH_MISMATCH = 0xC0000004.toInt()
    }
}