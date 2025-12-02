package dmitriy.losev.cs.exceptions

import io.ktor.http.HttpStatusCode

/**
 * Мапит исключения в HTTP статус коды
 */
object ErrorMapper {

    /**
     * Возвращает HTTP статус код для исключения
     */
    fun getHttpStatusCode(exception: Throwable): HttpStatusCode {
        return when (exception) {
            // Ошибки валидации
            is ValidationException -> HttpStatusCode.BadRequest
            is IllegalArgumentException,
            is IllegalStateException -> HttpStatusCode.BadRequest

            // Ошибки авторизации
            is UnauthorizedException -> HttpStatusCode.Unauthorized

            // Ошибки "не найдено"
            is NotFoundException -> HttpStatusCode.NotFound
            is io.ktor.server.plugins.NotFoundException -> HttpStatusCode.NotFound

            // Ошибки конфликта
            is ConflictException -> HttpStatusCode.Conflict

            // Сетевые ошибки
            is NetworkException -> HttpStatusCode.ServiceUnavailable

            // Ошибки Steam API
            is SteamApiException -> HttpStatusCode.BadGateway

            // Ошибки базы данных
            is DatabaseException -> HttpStatusCode.InternalServerError

            // Ошибки Steam
            is SteamException -> HttpStatusCode.InternalServerError

            // Все остальные ошибки приложения
            is BaseException -> HttpStatusCode.InternalServerError

            // Неизвестные ошибки
            else -> HttpStatusCode.InternalServerError
        }
    }

    /**
     * Возвращает код ошибки (простое имя класса)
     */
    fun getErrorCode(exception: Throwable): String {
        return exception::class.simpleName ?: "UNKNOWN_ERROR"
    }

    /**
     * Возвращает сообщение об ошибке
     */
    fun getErrorMessage(exception: Throwable): String {
        return exception.message ?: "Произошла неизвестная ошибка"
    }

    /**
     * Возвращает детали ошибки (для ValidationException)
     */
    fun getErrorDetails(exception: Throwable): List<String>? {
        return when (exception) {
            is ValidationException -> exception.errors
            else -> exception.cause?.message?.let { listOf(it) }
        }
    }
}
