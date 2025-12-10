package dmitriy.losev.cs.tasks

/**
 * Интерфейс для логирования выполнения задач.
 * Реализуй этот интерфейс для сохранения логов в БД.
 */
interface TaskLogger {

    /**
     * Вызывается при старте задачи.
     */
    suspend fun onTaskStarted(log: TaskExecutionLog)

    /**
     * Вызывается при успешном завершении задачи.
     */
    suspend fun onTaskCompleted(log: TaskExecutionLog)

    /**
     * Вызывается при ошибке в задаче.
     */
    suspend fun onTaskFailed(log: TaskExecutionLog)

    /**
     * Вызывается когда задача пропущена (предыдущая ещё выполняется).
     */
    suspend fun onTaskSkipped(log: TaskExecutionLog)
}
