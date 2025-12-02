package dmitriy.losev.cs.exceptions

/**
 * Исключение для ошибок при работе с Steam API (Web API)
 *
 * Примеры использования:
 * - throw SteamApiException("Не удалось получить инвентарь пользователя $steamId")
 * - throw SteamApiException("Steam API вернул ошибку: $errorMessage")
 * - throw SteamApiException("Превышен лимит запросов к Steam API", cause = httpException)
 */
class SteamApiException(message: String, cause: Throwable? = null) : BaseException(message, cause)
