package dmitriy.losev.cs.steam

import com.sun.jna.platform.win32.Kernel32
import com.sun.jna.platform.win32.Tlhelp32
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinNT
import mu.KotlinLogging

/**
 * Мониторинг процессов Steam
 */
class SteamProcessMonitor {

    private val logger = KotlinLogging.logger {}

    /**
     * Находит все процессы Steam.exe
     */
    fun findSteamProcesses(): List<ProcessInfo> {
        val processes = mutableListOf<ProcessInfo>()

        val snapshot = Kernel32.INSTANCE.CreateToolhelp32Snapshot(
            Tlhelp32.TH32CS_SNAPPROCESS,
            WinDef.DWORD(0)
        )

        if (snapshot == WinNT.INVALID_HANDLE_VALUE) {
            return processes
        }

        try {
            val entry = Tlhelp32.PROCESSENTRY32()

            if (Kernel32.INSTANCE.Process32First(snapshot, entry)) {
                do {
                    val processName = String(entry.szExeFile).trimEnd('\u0000')

                    if (processName.equals("steam.exe", ignoreCase = true)) {
                        processes.add(
                            ProcessInfo(
                                pid = entry.th32ProcessID.toInt(),
                                name = processName,
                                parentPid = entry.th32ParentProcessID.toInt()
                            )
                        )
                    }

                } while (Kernel32.INSTANCE.Process32Next(snapshot, entry))
            }
        } finally {
            Kernel32.INSTANCE.CloseHandle(snapshot)
        }

        return processes
    }

    /**
     * Ждёт появления нового процесса Steam (после перезапуска)
     */
    fun waitForSteamRestart(originalPid: Int, timeout: Long = 60000): Int? {
        logger.info { "Ожидание перезапуска Steam (оригинальный PID: $originalPid)..." }

        val startTime = System.currentTimeMillis()

        while (System.currentTimeMillis() - startTime < timeout) {
            val processes = findSteamProcesses()

            // Ищем новый процесс Steam с другим PID
            val newProcess = processes.find { it.pid != originalPid }

            if (newProcess != null) {
                logger.info { "✓ Steam перезапущен (новый PID: ${newProcess.pid})" }
                return newProcess.pid
            }

            Thread.sleep(1000)
        }

        logger.warn { "⚠ Steam не перезапустился" }
        return null
    }

    /**
     * Проверяет, жив ли процесс
     */
    fun isProcessAlive(pid: Int): Boolean {
        val handle = Kernel32.INSTANCE.OpenProcess(
            WinNT.PROCESS_QUERY_INFORMATION,
            false,
            pid
        )

        if (handle == null) return false

        val exitCode = com.sun.jna.ptr.IntByReference()
        val result = Kernel32.INSTANCE.GetExitCodeProcess(handle, exitCode)
        Kernel32.INSTANCE.CloseHandle(handle)

        return result && exitCode.value == 259 // STILL_ACTIVE
    }

    data class ProcessInfo(
        val pid: Int,
        val name: String,
        val parentPid: Int
    )
}