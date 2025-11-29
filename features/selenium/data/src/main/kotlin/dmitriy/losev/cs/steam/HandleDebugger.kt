package dmitriy.losev.cs.steam

// HandleDebugger.kt
import com.sun.jna.Memory
import com.sun.jna.Pointer
import com.sun.jna.platform.win32.*
import com.sun.jna.ptr.IntByReference
import mu.KotlinLogging
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

class HandleDebugger {

    private val kernel32 = Kernel32.INSTANCE
    private val ntdll = NtDll.INSTANCE

    companion object {
        const val SystemHandleInformation = 16
        const val ObjectNameInformation = 1
        const val STATUS_SUCCESS = 0
        const val STATUS_INFO_LENGTH_MISMATCH = 0xC0000004.toInt()
    }

    /**
     * Выводит все handles процесса с детальной информацией
     */
    fun analyzeProcessHandles(pid: Int) {
        println("\n" + "=".repeat(80))
        println("АНАЛИЗ HANDLES ПРОЦЕССА (PID: $pid)")
        println("=".repeat(80) + "\n")

        val processHandle = kernel32.OpenProcess(
            WinNTConstants.PROCESS_DUP_HANDLE or WinNTConstants.PROCESS_QUERY_INFORMATION,
            false,
            pid
        )

        if (processHandle == null) {
            println("❌ Не удалось открыть процесс (PID: $pid)")
            println("   Возможные причины:")
            println("   - Процесс не существует")
            println("   - Недостаточно прав (запустите от администратора)")
            return
        }

        try {
            // Получаем все handles системы
            val systemHandles = getSystemHandles()

            if (systemHandles.isEmpty()) {
                println("❌ Не удалось получить список handles системы")
                return
            }

            println("✓ Получено handles в системе: ${systemHandles.size}")

            // Фильтруем handles нашего процесса
            val processHandles = systemHandles.filter { it.pid == pid }

            println("✓ Handles процесса: ${processHandles.size}\n")

            if (processHandles.isEmpty()) {
                println("⚠ У процесса нет handles (процесс только что запущен?)")
                return
            }

            // Группируем по типам
            val byType = processHandles.groupBy { it.typeIndex }

            println("Распределение handles по типам:")
            println("-".repeat(80))
            byType.forEach { (typeIndex, handles) ->
                val typeName = getTypeName(typeIndex)
                println("  Type $typeIndex ($typeName): ${handles.size} handles")
            }
            println()

            // Получаем имена для каждого handle (с таймаутом)
            println("Получение имён handles...")
            val handlesWithNames = mutableListOf<HandleWithName>()

            var processed = 0
            processHandles.forEach { handle ->
                processed++
                if (processed % 50 == 0) {
                    print(".")
                }

                val name = getObjectNameSafe(processHandle, handle.value, 50)

                handlesWithNames.add(
                    HandleWithName(
                        value = handle.value,
                        typeIndex = handle.typeIndex,
                        objectName = name
                    )
                )
            }
            println(" Готово!\n")

            // Выводим handles с именами
            val namedHandles = handlesWithNames.filter { it.objectName != null }

            println("=".repeat(80))
            println("HANDLES С ИМЕНАМИ: ${namedHandles.size}")
            println("=".repeat(80) + "\n")

            if (namedHandles.isEmpty()) {
                println("⚠ НЕТ HANDLES С ИМЕНАМИ!")
                println("\nВозможные причины:")
                println("1. Процесс ещё не полностью инициализировался")
                println("2. Нужно больше времени ожидания")
                println("3. Handles находятся в другом процессе steam.exe")
                println("\nРекомендация: Подождите 15-30 секунд после запуска Steam")
            } else {
                // Группируем по типам
                val namedByType = namedHandles.groupBy { it.typeIndex }

                namedByType.forEach { (typeIndex, handles) ->
                    val typeName = getTypeName(typeIndex)

                    println("--- $typeName (Type $typeIndex) - ${handles.size} handles ---\n")

                    handles.forEach { handle ->
                        println("Handle: 0x${handle.value.toString(16).padStart(4, '0')}")
                        println("  Name: ${handle.objectName}")

                        // Подсвечиваем интересные handles
                        val name = handle.objectName?.lowercase() ?: ""
                        if (name.contains("steam") ||
                            name.contains("valve") ||
                            name.contains("ipc")) {
                            println("  ★★★ ПОТЕНЦИАЛЬНО ИНТЕРЕСНЫЙ ★★★")
                        }
                        println()
                    }
                }
            }

            println("=".repeat(80))

        } finally {
            kernel32.CloseHandle(processHandle)
        }
    }

    /**
     * Получает все handles системы
     */
    private fun getSystemHandles(): List<SystemHandle> {
        var bufferSize = 2 * 1024 * 1024 // 2MB
        var buffer: Pointer
        var status: Int

        // Увеличиваем буфер пока не получим все данные
        while (true) {
            buffer = Memory(bufferSize.toLong())
            val returnLength = IntByReference()

            status = ntdll.NtQuerySystemInformation(
                SystemHandleInformation,
                buffer,
                bufferSize,
                returnLength
            )

            when (status) {
                STATUS_SUCCESS -> break
                STATUS_INFO_LENGTH_MISMATCH -> {
                    bufferSize = returnLength.value
                    continue
                }
                else -> {
                    logger.error { "NtQuerySystemInformation failed: 0x${status.toString(16)}" }
                    return emptyList()
                }
            }
        }

        val handles = mutableListOf<SystemHandle>()
        val numberOfHandles = buffer.getInt(0)

        var offset = 8L // Пропускаем NumberOfHandles + padding

        repeat(numberOfHandles) {
            try {
                val pid = buffer.getShort(offset).toInt()
                val handleValue = buffer.getShort(offset + 4).toInt() and 0xFFFF
                val objectTypeIndex = buffer.getByte(offset + 2).toInt() and 0xFF

                handles.add(
                    SystemHandle(
                        pid = pid,
                        value = handleValue,
                        typeIndex = objectTypeIndex
                    )
                )
            } catch (e: Exception) {
                // Игнорируем ошибки парсинга отдельных handles
            }

            // Размер структуры SYSTEM_HANDLE_TABLE_ENTRY_INFO
            offset += if (com.sun.jna.Native.POINTER_SIZE == 8) 24L else 16L
        }

        return handles
    }

    /**
     * Получает имя объекта по handle (с таймаутом)
     */
    private fun getObjectNameSafe(
        processHandle: WinNT.HANDLE,
        handleValue: Int,
        timeoutMs: Long
    ): String? {
        return try {
            val future = CompletableFuture.supplyAsync {
                getObjectNameDirect(processHandle, handleValue)
            }

            future.get(timeoutMs, TimeUnit.MILLISECONDS)
        } catch (e: java.util.concurrent.TimeoutException) {
            null
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Получает имя объекта напрямую
     */
    private fun getObjectNameDirect(processHandle: WinNT.HANDLE, handleValue: Int): String? {
        try {
            // Дублируем handle
            val duplicatedHandle = WinNT.HANDLEByReference()

            val success = kernel32.DuplicateHandle(
                processHandle,
                WinNT.HANDLE(Pointer(handleValue.toLong())),
                kernel32.GetCurrentProcess(),
                duplicatedHandle,
                0,
                false,
                WinNTConstants.DUPLICATE_SAME_ACCESS
            )

            if (!success) return null

            try {
                val bufferSize = 2048
                val buffer = Memory(bufferSize.toLong())

                val status = ntdll.NtQueryObject(
                    duplicatedHandle.value,
                    ObjectNameInformation,
                    buffer,
                    bufferSize,
                    null
                )

                if (status == STATUS_SUCCESS) {
                    // UNICODE_STRING: Length (2 bytes), MaximumLength (2 bytes), Buffer (pointer)
                    val length = buffer.getShort(0).toInt()

                    if (length > 0 && length < 1000) {
                        val pointerSize = com.sun.jna.Native.POINTER_SIZE.toLong()
                        // Пропускаем Length и MaximumLength
                        val namePointer = buffer.getPointer(pointerSize * 2)

                        if (namePointer != null) {
                            return namePointer.getWideString(0)
                        }
                    }
                }
            } finally {
                kernel32.CloseHandle(duplicatedHandle.value)
            }
        } catch (e: Exception) {
            // Игнорируем ошибки
        }

        return null
    }

    /**
     * Получает имя типа по индексу
     */
    private fun getTypeName(typeIndex: Int): String {
        return when (typeIndex) {
            1 -> "Type"
            2 -> "Directory"
            3 -> "SymbolicLink"
            4 -> "Token"
            5 -> "Job"
            6 -> "Process"
            7 -> "Thread"
            8 -> "Event"
            9 -> "EventPair"
            10 -> "Mutant"
            11 -> "Callback"
            12 -> "Semaphore"
            13 -> "Timer"
            14 -> "Profile"
            15 -> "WindowStation"
            16 -> "Desktop"
            17 -> "Section"
            18 -> "Key"
            19 -> "Port"
            20 -> "WaitablePort"
            26 -> "File"
            else -> "Unknown"
        }
    }

    data class SystemHandle(
        val pid: Int,
        val value: Int,
        val typeIndex: Int
    )

    data class HandleWithName(
        val value: Int,
        val typeIndex: Int,
        val objectName: String?
    )
}