package dmitriy.losev.cs.steam

import com.sun.jna.platform.win32.Kernel32
import com.sun.jna.platform.win32.Tlhelp32
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinNT
import mu.KotlinLogging

class SteamProcessAnalyzer {

    private val logger = KotlinLogging.logger {}

    private val kernel32 = Kernel32.INSTANCE

    /**
     * Находит все процессы steam.exe
     */
    fun findAllSteamProcesses(): List<ProcessInfo> {
        val processes = mutableListOf<ProcessInfo>()

        val snapshot = kernel32.CreateToolhelp32Snapshot(
            WinDef.DWORD(Tlhelp32.TH32CS_SNAPPROCESS.toLong()),
            WinDef.DWORD(0)
        )

        if (snapshot == null || snapshot == WinNT.INVALID_HANDLE_VALUE) {
            logger.error { "Не удалось создать snapshot" }
            return processes
        }

        try {
            val entry = Tlhelp32.PROCESSENTRY32()

            if (kernel32.Process32First(snapshot, entry)) {
                do {
                    val processName = String(entry.szExeFile).trimEnd('\u0000')

                    if (processName.equals("steam.exe", ignoreCase = true) ||
                        processName.equals("steamwebhelper.exe", ignoreCase = true) ||
                        processName.equals("steamservice.exe", ignoreCase = true)) {

                        processes.add(
                            ProcessInfo(
                                pid = entry.th32ProcessID.toInt(),
                                name = processName,
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

    data class ProcessInfo(
        val pid: Int,
        val name: String,
        val parentPid: Int
    )
}