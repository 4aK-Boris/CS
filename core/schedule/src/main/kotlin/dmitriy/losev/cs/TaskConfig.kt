package dmitriy.losev.cs

/**
 * Конфигурация задачи с зависимостями.
 *
 * @param awaitCompletion список taskId, завершения которых нужно дождаться перед запуском
 * @param skipIfActive список taskId — если любая из этих задач активна, текущая задача пропускается
 */
data class TaskConfig(
    val awaitCompletion: Set<String> = emptySet(),
    val skipIfActive: Set<String> = emptySet()
)
