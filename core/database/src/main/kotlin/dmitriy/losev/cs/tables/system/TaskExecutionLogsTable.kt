package dmitriy.losev.cs.tables.system

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.javatime.timestamp

object TaskExecutionLogsTable : Table(name = "system.task_execution_logs") {

    val id = long(name = "id").autoIncrement()

    val taskId = varchar(name = "task_id", length = 128)

    val status = varchar(name = "status", length = 16)

    val startedAt = timestamp(name = "started_at")

    val finishedAt = timestamp(name = "finished_at").nullable()

    val durationMillis = long(name = "duration_millis").nullable()

    val errorMessage = text(name = "error_message").nullable()

    val errorCount = integer(name = "error_count").default(0)

    val successCount = integer(name = "success_count").default(0)

    val totalCount = integer(name = "total_count").default(0)

    val data = text(name = "data").nullable()

    val createdAt = timestamp(name = "created_at")

    override val primaryKey = PrimaryKey(id)
}
