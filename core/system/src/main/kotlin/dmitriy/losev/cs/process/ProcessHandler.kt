package dmitriy.losev.cs.process

import com.sun.jna.platform.win32.Kernel32
import com.sun.jna.platform.win32.Tlhelp32
import com.sun.jna.platform.win32.Tlhelp32.TH32CS_SNAPPROCESS
import com.sun.jna.platform.win32.WinBase
import com.sun.jna.platform.win32.WinDef.DWORD
import org.koin.core.annotation.Singleton

@Singleton
class ProcessHandler(
    private val kernel32: Kernel32
) {

    fun createProcess(
        command: String,
        params: List<String> = emptyList(),
        environmentVariables: Map<String, String> = emptyMap()
    ): Long {

        val fullCommand = buildList {
            add(element = command)
            addAll(elements = params)
        }

        val processBuilder = ProcessBuilder(fullCommand)

        processBuilder.environment().apply {
            environmentVariables.forEach { (name, value) ->
                set(key = name, value = value)
            }
        }

        val process = processBuilder.start()

        return process.pid()
    }

    fun findCS2FromSteam(steamProcessId: Long): Long? {

        val children = getChildProcessesInfo(parentPid = steamProcessId)

        for (child in children) {

            if (child.name.equals(other = CS2_PROCESS_NAME, ignoreCase = true)) {
                return child.pid
            }

            val grandChild = findCS2FromSteam(steamProcessId = child.pid)

            if (grandChild != null) {
                return grandChild
            }
        }

        return null
    }

    fun getAllChildProcesses(parentProcessId: Long): List<Long> {
        val children = getChildProcessesIds(parentPid = parentProcessId)
        return children + children.flatMap { processId -> getAllChildProcesses(parentProcessId = processId) }
    }

    fun getChildProcessesInfo(parentPid: Long): List<ProcessInfo> {

        val children = mutableListOf<ProcessInfo>()

        val snapshot = kernel32.CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, DWORD(0))

        if (snapshot == WinBase.INVALID_HANDLE_VALUE) {
            return children
        }

        try {

            val processEntry = Tlhelp32.PROCESSENTRY32.ByReference()

            if (kernel32.Process32First(snapshot, processEntry)) {

                do {
                    if (processEntry.th32ParentProcessID.toLong() == parentPid) {
                        val exeName = String(processEntry.szExeFile).trimEnd('\u0000')
                        children.add(ProcessInfo(pid = processEntry.th32ProcessID.toLong(), name = exeName, parentPid = parentPid))
                    }
                } while (kernel32.Process32Next(snapshot, processEntry))
            }
        } finally {
            kernel32.CloseHandle(snapshot)
        }

        return children
    }

    fun getChildProcessesIds(parentPid: Long): List<Long> {

        val children = mutableListOf<Long>()

        val snapshot = kernel32.CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, DWORD(0))

        if (snapshot == WinBase.INVALID_HANDLE_VALUE) {
            return children
        }

        try {

            val processEntry = Tlhelp32.PROCESSENTRY32.ByReference()

            if (kernel32.Process32First(snapshot, processEntry)) {

                do {
                    if (processEntry.th32ParentProcessID.toLong() == parentPid) {
                        children.add(processEntry.th32ProcessID.toLong())
                    }
                } while (kernel32.Process32Next(snapshot, processEntry))
            }
        } finally {
            kernel32.CloseHandle(snapshot)
        }

        return children
    }

    companion object {
        private const val CS2_PROCESS_NAME = "cs2.exe"
    }
}