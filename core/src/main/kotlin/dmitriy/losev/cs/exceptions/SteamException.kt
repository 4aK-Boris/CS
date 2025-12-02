package dmitriy.losev.cs.exceptions

/**
 * Исключение для ошибок при работе с Steam (клиентом, процессами, куками и т.д.)
 *
 * Примеры использования:
 * - throw SteamException("Куки SteamLoginSecure для аккаунта $steamId имеет значение null")
 * - throw SteamException("Куки для аккаунта $steamId протухли")
 * - throw SteamException("Не удалось найти процесс CS2 по id $steamProcessId")
 */
class SteamException(message: String, cause: Throwable? = null) : BaseException(message, cause)
