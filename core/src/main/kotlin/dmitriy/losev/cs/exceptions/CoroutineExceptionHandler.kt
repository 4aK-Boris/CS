package dmitriy.losev.cs.exceptions

import kotlinx.coroutines.CoroutineExceptionHandler

/**
 * Extension функция для удобного создания обработчика исключений
 */
fun CoroutineExceptionHandler(taskName: String, onError: ((Throwable) -> Unit)? = null): CoroutineExceptionHandler {
    return TaskExceptionHandler.create(taskName, onError)
}
