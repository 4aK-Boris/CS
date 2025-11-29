package dmitriy.losev.cs.steam

object WinRegistryConstants {
    // Registry Options
    const val REG_OPTION_NON_VOLATILE = 0x00000000
    const val REG_OPTION_VOLATILE = 0x00000001
    const val REG_OPTION_CREATE_LINK = 0x00000002
    const val REG_OPTION_BACKUP_RESTORE = 0x00000004

    // Registry Value Types
    const val REG_NONE = 0
    const val REG_SZ = 1
    const val REG_EXPAND_SZ = 2
    const val REG_BINARY = 3
    const val REG_DWORD = 4
    const val REG_DWORD_BIG_ENDIAN = 5
    const val REG_LINK = 6
    const val REG_MULTI_SZ = 7
    const val REG_RESOURCE_LIST = 8
    const val REG_FULL_RESOURCE_DESCRIPTOR = 9
    const val REG_RESOURCE_REQUIREMENTS_LIST = 10
    const val REG_QWORD = 11

    // Error Codes
    const val ERROR_SUCCESS = 0
    const val ERROR_FILE_NOT_FOUND = 2
    const val ERROR_ACCESS_DENIED = 5
    const val ERROR_INVALID_HANDLE = 6
    const val ERROR_NOT_ENOUGH_MEMORY = 8
    const val ERROR_INVALID_PARAMETER = 87
    const val ERROR_MORE_DATA = 234
    const val ERROR_NO_MORE_ITEMS = 259

    // Access Rights
    const val KEY_QUERY_VALUE = 0x0001
    const val KEY_SET_VALUE = 0x0002
    const val KEY_CREATE_SUB_KEY = 0x0004
    const val KEY_ENUMERATE_SUB_KEYS = 0x0008
    const val KEY_NOTIFY = 0x0010
    const val KEY_CREATE_LINK = 0x0020
    const val KEY_WOW64_64KEY = 0x0100
    const val KEY_WOW64_32KEY = 0x0200

    const val KEY_READ = 0x20019  // STANDARD_RIGHTS_READ | KEY_QUERY_VALUE | KEY_ENUMERATE_SUB_KEYS | KEY_NOTIFY
    const val KEY_WRITE = 0x20006  // STANDARD_RIGHTS_WRITE | KEY_SET_VALUE | KEY_CREATE_SUB_KEY
    const val KEY_ALL_ACCESS = 0xF003F

    // DuplicateHandle flags
    const val DUPLICATE_CLOSE_SOURCE = 0x00000001
    const val DUPLICATE_SAME_ACCESS = 0x00000002

    // Process access rights
    const val PROCESS_DUP_HANDLE = 0x0040
    const val PROCESS_QUERY_INFORMATION = 0x0400
    const val PROCESS_VM_READ = 0x0010

    // Handle flags
    const val HANDLE_FLAG_INHERIT = 0x00000001
    const val HANDLE_FLAG_PROTECT_FROM_CLOSE = 0x00000002

    // Registry creation disposition
    const val REG_CREATED_NEW_KEY = 0x00000001
    const val REG_OPENED_EXISTING_KEY = 0x00000002
}