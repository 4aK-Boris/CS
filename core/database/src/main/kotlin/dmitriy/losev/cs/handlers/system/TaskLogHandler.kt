package dmitriy.losev.cs.handlers.system

import dmitriy.losev.cs.tasks.TaskExecutionLog

interface TaskLogHandler {

    suspend fun insertLog(log: TaskExecutionLog)

    suspend fun getLogsByTaskId(taskId: String, limit: Int = 100): List<TaskExecutionLog>

    suspend fun getLatestLogs(limit: Int = 100): List<TaskExecutionLog>

    suspend fun deleteOldLogs(daysToKeep: Int = 30): Int
}
