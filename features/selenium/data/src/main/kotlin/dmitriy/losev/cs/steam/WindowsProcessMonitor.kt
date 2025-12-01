package dmitriy.losev.cs.steam

import com.sun.jna.Native
import com.sun.jna.platform.win32.Kernel32
import com.sun.jna.platform.win32.Tlhelp32
import com.sun.jna.platform.win32.WinBase
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinNT
import com.sun.jna.ptr.IntByReference
import mu.KotlinLogging

private val monitorLogger = KotlinLogging.logger("WindowsProcessMonitor")

data class ProcessInfo(
    val pid: Int,
    val name: String
)

class WindowsProcessMonitor {

    fun findSteamProcesses(): List<ProcessInfo> {
        return findProcessesByName("steam.exe")
    }

    fun findCS2Processes(): List<ProcessInfo> {
        return findProcessesByName("cs2.exe")
    }

    fun getAllSteamProcesses(): List<ProcessInfo> {
        val steamProcessNames = listOf(
            "steam.exe",
            "steamwebhelper.exe",
            "steamerrorreporter.exe",
            "streaming_client.exe"
        )

        return steamProcessNames.flatMap { findProcessesByName(it) }
    }

    fun isProcessRunning(pid: Int): Boolean {
        val handle = Kernel32.INSTANCE.OpenProcess(
            WinNT.PROCESS_QUERY_INFORMATION,
            false,
            pid
        ) ?: return false

        val exitCode = IntByReference()
        val result = Kernel32.INSTANCE.GetExitCodeProcess(handle, exitCode)
        Kernel32.INSTANCE.CloseHandle(handle)

        return result && exitCode.value == WinBase.STILL_ACTIVE
    }

    fun killProcess(pid: Int) {
        try {
            ProcessBuilder("taskkill", "/F", "/PID", pid.toString())
                .start()
                .waitFor()
            monitorLogger.info { "Процесс $pid остановлен" }
        } catch (e: Exception) {
            monitorLogger.warn { "Не удалось остановить процесс $pid: ${e.message}" }
        }
    }

    fun killAllSteamProcesses() {
        val processNames = listOf(
            "steam.exe",
            "steamwebhelper.exe",
            "steamerrorreporter.exe",
            "steamservice.exe"
        )

        processNames.forEach { name ->
            try {
                ProcessBuilder("taskkill", "/F", "/IM", name)
                    .start()
                    .waitFor()
            } catch (e: Exception) {
                // Игнорируем ошибки, процесс может не существовать
            }
        }

        Thread.sleep(1000)
    }

    private fun findProcessesByName(processName: String): List<ProcessInfo> {
        val processes = mutableListOf<ProcessInfo>()
        val snapshot = Kernel32.INSTANCE.CreateToolhelp32Snapshot(
            Tlhelp32.TH32CS_SNAPPROCESS,
            WinDef.DWORD(0)
        )

        try {
            val entry = Tlhelp32.PROCESSENTRY32.ByReference()

            if (Kernel32.INSTANCE.Process32First(snapshot, entry)) {
                do {
                    val exeFile = Native.toString(entry.szExeFile)
                    if (exeFile.equals(processName, ignoreCase = true)) {
                        processes.add(
                            ProcessInfo(
                                pid = entry.th32ProcessID.toInt(),
                                name = exeFile
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
}
