package dmitriy.losev.cs.handlers.system

import dmitriy.losev.cs.Database
import dmitriy.losev.cs.tables.system.TaskExecutionLogsTable
import dmitriy.losev.cs.tasks.TaskExecutionLog
import dmitriy.losev.cs.tasks.TaskStatus
import java.time.Instant
import java.time.temporal.ChronoUnit
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.less
import org.jetbrains.exposed.v1.r2dbc.deleteWhere
import org.jetbrains.exposed.v1.r2dbc.insert
import org.jetbrains.exposed.v1.r2dbc.selectAll

internal class TaskLogHandlerImpl(private val database: Database) : TaskLogHandler {

    override suspend fun insertLog(log: TaskExecutionLog): Unit = database.suspendTransaction {

        TaskExecutionLogsTable.insert { insertStatement ->
            insertStatement[taskId] = log.taskId
            insertStatement[status] = log.status.name
            insertStatement[startedAt] = log.startedAt
            insertStatement[finishedAt] = log.finishedAt
            insertStatement[durationMillis] = log.durationMillis
            insertStatement[errorMessage] = log.errorMessage
            insertStatement[errorCount] = log.errorCount
            insertStatement[successCount] = log.successCount
            insertStatement[totalCount] = log.totalCount
            insertStatement[data] = log.data
            insertStatement[createdAt] = Instant.now()
        }
    }

    override suspend fun getLogsByTaskId(taskId: String, limit: Int): List<TaskExecutionLog> = database.suspendTransaction {
            TaskExecutionLogsTable
                .selectAll()
                .where { TaskExecutionLogsTable.taskId eq taskId }
                .orderBy(TaskExecutionLogsTable.startedAt, SortOrder.DESC)
                .limit(limit)
                .map(::convertToTaskExecutionLog)
                .toList()
        }

    override suspend fun getLatestLogs(limit: Int): List<TaskExecutionLog> = database.suspendTransaction {
        TaskExecutionLogsTable
            .selectAll()
            .orderBy(TaskExecutionLogsTable.startedAt, SortOrder.DESC)
            .limit(limit)
            .map(::convertToTaskExecutionLog)
            .toList()
    }

    override suspend fun deleteOldLogs(daysToKeep: Int): Int = database.suspendTransaction {
        val cutoffDate = Instant.now().minus(daysToKeep.toLong(), ChronoUnit.DAYS)
        TaskExecutionLogsTable.deleteWhere {
            TaskExecutionLogsTable.createdAt less cutoffDate
        }
    }

    private fun convertToTaskExecutionLog(row: ResultRow): TaskExecutionLog {
        return TaskExecutionLog(
            taskId = row[TaskExecutionLogsTable.taskId],
            status = TaskStatus.valueOf(row[TaskExecutionLogsTable.status]),
            startedAt = row[TaskExecutionLogsTable.startedAt],
            finishedAt = row[TaskExecutionLogsTable.finishedAt],
            durationMillis = row[TaskExecutionLogsTable.durationMillis],
            errorMessage = row[TaskExecutionLogsTable.errorMessage],
            errorCount = row[TaskExecutionLogsTable.errorCount],
            successCount = row[TaskExecutionLogsTable.successCount],
            totalCount = row[TaskExecutionLogsTable.totalCount],
            data = row[TaskExecutionLogsTable.data]
        )
    }
}
