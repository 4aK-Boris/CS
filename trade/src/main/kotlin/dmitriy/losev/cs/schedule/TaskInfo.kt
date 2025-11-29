package dmitriy.losev.cs.schedule

/** Информация о задаче. */
data class TaskInfo(
    val taskId: String,
    val isScheduled: Boolean,
    val currentConcurrency: Int,
    val maxConcurrency: Int
)