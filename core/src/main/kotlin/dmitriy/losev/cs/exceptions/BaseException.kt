package dmitriy.losev.cs.exceptions

/**
 * Базовое исключение для общих ошибок приложения
 */
abstract class BaseException(override val message: String? = null, override val cause: Throwable? = null) : Exception(message, cause)
