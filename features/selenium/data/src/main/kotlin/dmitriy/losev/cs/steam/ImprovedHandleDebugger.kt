package dmitriy.losev.cs.steam

import com.sun.jna.Pointer
import com.sun.jna.platform.win32.Kernel32
import com.sun.jna.platform.win32.WinNT
import com.sun.jna.ptr.IntByReference

class ImprovedHandleDebugger {

    private val kernel32 = Kernel32.INSTANCE
    private val ntdll = NtDll.INSTANCE

    fun listAllHandlesDetailed(pid: Int) {
        println("\n" + "=".repeat(80))
        println("Детальный анализ handles для PID: $pid")
        println("=".repeat(80) + "\n")

        val processHandle = kernel32.OpenProcess(
            WinRegistryConstants.PROCESS_DUP_HANDLE or WinRegistryConstants.PROCESS_QUERY_INFORMATION,
            false,
            pid
        )

        if (processHandle == null) {
            println("❌ Не удалось открыть процесс")
            return
        }

        try {
            var bufferSize = 2 * 1024 * 1024 // 2MB для большей надёжности
            var buffer: Pointer
            var status: Int

            while (true) {
                buffer = com.sun.jna.Memory(bufferSize.toLong())
                val returnLength = IntByReference()

                status = ntdll.NtQuerySystemInformation(16, buffer, bufferSize, returnLength)

                if (status == 0) break
                if (status == 0xC0000004.toInt()) {
                    bufferSize = returnLength.value
                    continue
                }

                println("❌ NtQuerySystemInformation failed: 0x${status.toString(16)}")
                return
            }

            val numberOfHandles = buffer.getInt(0)
            println("Всего handles в системе: $numberOfHandles")

            var offset = 8L
            val foundHandles = mutableListOf<DetailedHandleInfo>()

            repeat(numberOfHandles) {
                try {
                    val handlePid = buffer.getShort(offset).toInt()

                    if (handlePid == pid) {
                        val handleValue = buffer.getShort(offset + 4).toInt() and 0xFFFF
                        val objectTypeIndex = buffer.getByte(offset + 2).toInt() and 0xFF

                        // Пытаемся получить имя для ВСЕХ handles
                        val objectName = getObjectNameWithTimeout(processHandle, handleValue, 50)

                        foundHandles.add(
                            DetailedHandleInfo(
                                value = handleValue,
                                typeIndex = objectTypeIndex,
                                objectName = objectName
                            )
                        )
                    }

                } catch (e: Exception) {
                    // Игнорируем
                }

                offset += if (com.sun.jna.Native.POINTER_SIZE == 8) 24L else 16L
            }

            println("Найдено handles для процесса: ${foundHandles.size}\n")

            // Группируем по типам
            val grouped = foundHandles.groupBy { it.typeIndex }

            println("Handles по типам:")
            println("-".repeat(80))
            grouped.forEach { (typeIndex, handles) ->
                println("Type ${typeIndex}: ${handles.size} handles")
            }

            println("\n" + "=".repeat(80))
            println("Handles с именами (любые типы):")
            println("=".repeat(80) + "\n")

            val namedHandles = foundHandles.filter { it.objectName != null }

            if (namedHandles.isEmpty()) {
                println("⚠ НЕТ HANDLES С ИМЕНАМИ!")
                println("\nВозможные причины:")
                println("1. Steam ещё не инициализировался полностью")
                println("2. Handle находится в другом процессе steam.exe")
                println("3. Нужно больше времени ожидания")
            } else {
                namedHandles.sortedBy { it.typeIndex }.forEach { handle ->
                    println("Handle: 0x${handle.value.toString(16).padStart(4, '0')}")
                    println("  Type Index: ${handle.typeIndex}")
                    println("  Name: ${handle.objectName}")

                    // Подсвечиваем если содержит Steam/Valve/IPC
                    if (handle.objectName?.contains("steam", ignoreCase = true) == true ||
                        handle.objectName?.contains("valve", ignoreCase = true) == true ||
                        handle.objectName?.contains("ipc", ignoreCase = true) == true) {
                        println("  ★★★ ПОТЕНЦИАЛЬНО ИНТЕРЕСНЫЙ HANDLE ★★★")
                    }
                    println()
                }
            }

            println("=".repeat(80))

        } finally {
            kernel32.CloseHandle(processHandle)
        }
    }

    private fun getObjectNameWithTimeout(
        processHandle: WinNT.HANDLE,
        handleValue: Int,
        timeoutMs: Long
    ): String? {
        return try {
            val future = java.util.concurrent.CompletableFuture.supplyAsync {
                getObjectNameDirect(processHandle, handleValue)
            }

            future.get(timeoutMs, java.util.concurrent.TimeUnit.MILLISECONDS)
        } catch (e: java.util.concurrent.TimeoutException) {
            null
        } catch (e: Exception) {
            null
        }
    }

    private fun getObjectNameDirect(processHandle: WinNT.HANDLE, handleValue: Int): String? {
        try {
            val duplicatedHandle = WinNT.HANDLEByReference()

            if (!kernel32.DuplicateHandle(
                    processHandle,
                    WinNT.HANDLE(Pointer(handleValue.toLong())),
                    kernel32.GetCurrentProcess(),
                    duplicatedHandle,
                    0,
                    false,
                    WinRegistryConstants.DUPLICATE_SAME_ACCESS
                )) return null

            try {
                val buffer = com.sun.jna.Memory(2048)
                val status = ntdll.NtQueryObject(duplicatedHandle.value, 1, buffer, 2048, null)

                if (status == 0) {
                    val length = buffer.getShort(0).toInt()
                    if (length > 0 && length < 1000) {
                        val pointerSize = com.sun.jna.Native.POINTER_SIZE.toLong()
                        val namePointer = buffer.getPointer(pointerSize * 2)
                        return namePointer?.getWideString(0)
                    }
                }
            } finally {
                kernel32.CloseHandle(duplicatedHandle.value)
            }
        } catch (e: Exception) {
            // ignore
        }
        return null
    }

    data class DetailedHandleInfo(
        val value: Int,
        val typeIndex: Int,
        val objectName: String?
    )
}