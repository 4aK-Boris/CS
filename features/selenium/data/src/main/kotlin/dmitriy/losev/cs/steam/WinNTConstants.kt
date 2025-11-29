package dmitriy.losev.cs.steam

// WinNTConstants.kt
object WinNTConstants {
    // DuplicateHandle flags
    const val DUPLICATE_CLOSE_SOURCE = 0x00000001
    const val DUPLICATE_SAME_ACCESS = 0x00000002

    // Process access rights
    const val PROCESS_DUP_HANDLE = 0x0040
    const val PROCESS_QUERY_INFORMATION = 0x0400
    const val PROCESS_VM_READ = 0x0010
    const val PROCESS_ALL_ACCESS = 0x1F0FFF

    // Thread access rights
    const val THREAD_SUSPEND_RESUME = 0x0002

    // Registry constants
    const val REG_OPTION_NON_VOLATILE = 0x00000000
    const val REG_SZ = 1
    const val REG_DWORD = 4
    const val REG_CREATED_NEW_KEY = 0x00000001

    // Access rights
    const val KEY_READ = 0x20019
    const val KEY_WRITE = 0x20006
    const val KEY_ALL_ACCESS = 0xF003F
}