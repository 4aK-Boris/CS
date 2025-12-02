package dmitriy.losev.cs.exceptions

import kotlinx.coroutines.CoroutineExceptionHandler
import org.slf4j.LoggerFactory
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

/**
 * Обработчик исключений для фоновых задач (корутин)
 */
class TaskExceptionHandler(
    private val taskName: String,
    private val onError: ((Throwable) -> Unit)? = null
) : AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {

    private val logger = LoggerFactory.getLogger(TaskExceptionHandler::class.java)

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        logException(exception)
        onError?.invoke(exception)
    }

    private fun logException(exception: Throwable) {
        val exceptionType = exception::class.simpleName ?: "Unknown"

        when (exception) {
            is BaseException -> logger.error("[Task: $taskName] $exceptionType: ${exception.message}", exception)
            is IllegalArgumentException, is IllegalStateException -> logger.warn("[Task: $taskName] Validation error: ${exception.message}", exception)
            else -> logger.error("[Task: $taskName] $exceptionType: ${exception.message}", exception)
        }
    }

    companion object {
        /**
         * Создает обработчик исключений для фоновой задачи
         */
        fun create(taskName: String, onError: ((Throwable) -> Unit)? = null): TaskExceptionHandler {
            return TaskExceptionHandler(taskName, onError)
        }
    }
}
