package dmitriy.losev.cs.exceptions

import dmitriy.losev.cs.api.ApiResponse
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import org.slf4j.LoggerFactory

/**
 * Универсальный обработчик исключений
 */
object ExceptionHandler {

    private val logger = LoggerFactory.getLogger(ExceptionHandler::class.java)

    /**
     * Обрабатывает исключение для HTTP запроса
     */
    suspend fun handleHttpException(call: ApplicationCall, exception: Throwable) {
        logException(exception, "HTTP request")

        val statusCode = ErrorMapper.getHttpStatusCode(exception)
        val errorCode = ErrorMapper.getErrorCode(exception)
        val message = ErrorMapper.getErrorMessage(exception)
        val details = ErrorMapper.getErrorDetails(exception)

        call.respond(statusCode, ApiResponse.Error(message, errorCode, details))
    }

    /**
     * Обрабатывает исключение для фоновой задачи
     */
    fun handleTaskException(taskName: String, exception: Throwable) {
        logException(exception, "Background task: $taskName")
    }

    /**
     * Создает ErrorResponse из исключения
     */
    fun createErrorResponse(exception: Throwable): ApiResponse.Error {
        val errorCode = ErrorMapper.getErrorCode(exception)
        val message = ErrorMapper.getErrorMessage(exception)

        return ApiResponse.Error(
            message = message,
            code = errorCode,
            details = exception.cause?.message?.let { listOf(it) }
        )
    }

    /**
     * Логирует исключение
     */
    private fun logException(exception: Throwable, context: String) {
        val exceptionType = exception::class.simpleName ?: "Unknown"

        when (exception) {
            is BaseException -> logger.error("[$context] $exceptionType: ${exception.message}", exception)
            is IllegalArgumentException, is IllegalStateException ->  logger.warn("[$context] Validation error: ${exception.message}", exception)
            else -> logger.error("[$context] $exceptionType: ${exception.message}", exception)
        }
    }
}
