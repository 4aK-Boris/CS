package dmitriy.losev.cs.tasks

import java.time.Instant

/**
 * Лог выполнения задачи.
 *
 * @param taskId идентификатор задачи
 * @param status статус выполнения
 * @param startedAt время начала
 * @param finishedAt время завершения
 * @param durationMillis длительность в миллисекундах
 * @param errorMessage сообщение об ошибке
 * @param errorCount количество ошибок
 * @param successCount количество успешных операций
 * @param totalCount общее количество операций
 * @param data данные результата выполнения (для записи в БД)
 */
data class TaskExecutionLog(
    val taskId: String,
    val status: TaskStatus,
    val startedAt: Instant,
    val finishedAt: Instant? = null,
    val durationMillis: Long? = null,
    val errorMessage: String? = null,
    val errorCount: Int = 0,
    val successCount: Int = 0,
    val totalCount: Int = 0,
    val data: String? = null
)
