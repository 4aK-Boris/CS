package dmitriy.losev.cs

/**
 * Результат выполнения задачи с агрегацией ошибок и данными.
 *
 * @param T тип данных результата
 * @param successCount количество успешных операций
 * @param errorCount количество ошибок
 * @param errors список ошибок
 * @param data данные успешного выполнения (для записи в БД)
 */
data class TaskResult<out T>(
    val successCount: Int,
    val errorCount: Int,
    val errors: List<TaskError>,
    val data: T? = null
) {
    val totalCount: Int get() = successCount + errorCount
    val isSuccess: Boolean get() = errorCount == 0
    val hasErrors: Boolean get() = errorCount > 0

    companion object {

        fun success(): TaskResult<Unit> = TaskResult(
            successCount = 1,
            errorCount = 0,
            errors = emptyList(),
            data = Unit
        )

        fun <T> success(data: T): TaskResult<T> = TaskResult(
            successCount = 1,
            errorCount = 0,
            errors = emptyList(),
            data = data
        )

        fun failure(error: Throwable, context: String? = null): TaskResult<Nothing> = TaskResult(
            successCount = 0,
            errorCount = 1,
            errors = listOf(TaskError(error.message ?: "Unknown error", context)),
            data = null
        )

        fun failure(message: String, context: String? = null): TaskResult<Nothing> = TaskResult(
            successCount = 0,
            errorCount = 1,
            errors = listOf(TaskError(message, context)),
            data = null
        )

        /**
         * Из Result<T> — один результат.
         */
        fun <T> fromResult(result: Result<T>, context: String? = null): TaskResult<T> {
            return result.fold(
                onSuccess = ::success,
                onFailure = { error -> failure(error, context) }
            )
        }

        /**
         * Из List<Result<T>> — агрегация нескольких результатов.
         * Возвращает список успешных значений.
         */
        fun <T> fromResults(results: List<Result<T>>): TaskResult<List<T>> {

            val errors = mutableListOf<TaskError>()
            val successData = mutableListOf<T>()
            var errorCount = 0

            results.forEach { result ->
                result.fold(
                    onSuccess = successData::add,
                    onFailure = { throwable ->
                        errorCount++
                        errors.add(TaskError(throwable.message ?: "Unknown error"))
                    }
                )
            }

            return TaskResult(
                successCount = successData.size,
                errorCount = errorCount,
                errors = errors,
                data = successData
            )
        }

        /**
         * Из Result<List<Result<T>>> — вложенный результат.
         * Если внешний Result — ошибка, возвращает ошибку.
         * Если успех — агрегирует внутренний список.
         */
        fun <T> fromNestedResults(result: Result<List<Result<T>>>, context: String? = null): TaskResult<List<T>> {
            return result.fold(
                onSuccess = ::fromResults,
                onFailure = { error ->
                    TaskResult(
                        successCount = 0,
                        errorCount = 1,
                        errors = listOf(TaskError(error.message ?: "Unknown error", context)),
                        data = emptyList()
                    )
                }
            )
        }

        /**
         * Объединяет несколько TaskResult в один.
         */
        fun <T> merge(results: List<TaskResult<List<T>>>): TaskResult<List<T>> {
            return TaskResult(
                successCount = results.sumOf(selector = TaskResult<List<T>>::successCount),
                errorCount = results.sumOf(selector = TaskResult<List<T>>::errorCount),
                errors = results.flatMap(transform = TaskResult<List<T>>::errors),
                data = results.flatMap { data -> data.data.orEmpty() }
            )
        }
    }
}
