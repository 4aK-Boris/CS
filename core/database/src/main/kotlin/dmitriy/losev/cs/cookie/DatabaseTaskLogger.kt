package dmitriy.losev.cs.cookie

import dmitriy.losev.cs.handlers.system.TaskLogHandler
import dmitriy.losev.cs.tasks.TaskExecutionLog
import dmitriy.losev.cs.tasks.TaskLogger

class DatabaseTaskLogger(private val taskLogHandler: TaskLogHandler) : TaskLogger {

    override suspend fun onTaskStarted(log: TaskExecutionLog) {
        taskLogHandler.insertLog(log)
    }

    override suspend fun onTaskCompleted(log: TaskExecutionLog) {
        taskLogHandler.insertLog(log)
    }

    override suspend fun onTaskFailed(log: TaskExecutionLog) {
        taskLogHandler.insertLog(log)
    }

    override suspend fun onTaskSkipped(log: TaskExecutionLog) {
        taskLogHandler.insertLog(log)
    }
}
