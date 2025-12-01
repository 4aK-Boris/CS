package dmitriy.losev.cs.steam

// ProcessUtils.kt
import com.sun.jna.platform.win32.Kernel32
import com.sun.jna.platform.win32.Tlhelp32
import com.sun.jna.platform.win32.WinDef.DWORD
import com.sun.jna.platform.win32.WinNT
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

object ProcessUtils {

    private val kernel32 = Kernel32.INSTANCE

    /**
     * Находит все процессы по имени
     */
    fun findProcessesByName(processName: String): List<ProcessInfo> {
        val processes = mutableListOf<ProcessInfo>()

        val snapshot = kernel32.CreateToolhelp32Snapshot(
            DWORD(Tlhelp32.TH32CS_SNAPPROCESS.toLong()),
            DWORD(0)
        )

        if (snapshot == null || snapshot == WinNT.INVALID_HANDLE_VALUE) {
            logger.error { "Не удалось создать snapshot" }
            return processes
        }

        try {
            val entry = Tlhelp32.PROCESSENTRY32()

            if (kernel32.Process32First(snapshot, entry)) {
                do {
                    val currentName = String(entry.szExeFile).trimEnd('\u0000')

                    if (currentName.equals(processName, ignoreCase = true)) {
                        processes.add(
                            ProcessInfo(
                                pid = entry.th32ProcessID.toInt(),
                                name = currentName,
                                parentPid = entry.th32ParentProcessID.toInt()
                            )
                        )
                    }

                } while (kernel32.Process32Next(snapshot, entry))
            }
        } finally {
            kernel32.CloseHandle(snapshot)
        }

        return processes
    }

    /**
     * Убивает все процессы по имени
     */
    fun killProcessByName(processName: String): Boolean {
        return try {
            val process = Runtime.getRuntime().exec("taskkill /F /IM $processName")
            process.waitFor()
            true
        } catch (e: Exception) {
            logger.error(e) { "Ошибка завершения процесса $processName" }
            false
        }
    }

    /**
     * Проверяет, существует ли процесс
     */
    fun isProcessRunning(pid: Int): Boolean {
        val handle = kernel32.OpenProcess(
            WinNTConstants.PROCESS_QUERY_INFORMATION,
            false,
            pid
        ) ?: return false

        val exitCode = com.sun.jna.ptr.IntByReference()
        val result = kernel32.GetExitCodeProcess(handle, exitCode)
        kernel32.CloseHandle(handle)

        return result && exitCode.value == 259 // STILL_ACTIVE
    }

    data class ProcessInfo(
        val pid: Int,
        val name: String,
        val parentPid: Int
    )
}