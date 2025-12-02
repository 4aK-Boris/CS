package dmitriy.losev.cs.exceptions

/**
 * Исключение для сетевых ошибок (HTTP, сокеты и т.д.)
 *
 * Примеры использования:
 * - throw NetworkException("Не удалось подключиться к серверу")
 * - throw NetworkException("Таймаут при ожидании ответа", cause = ioException)
 * - throw NetworkException("Не удалось найти прокси для steam аккаунта $steamId")
 */
class NetworkException(message: String, cause: Throwable? = null) : BaseException(message, cause)
