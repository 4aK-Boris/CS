package dmitriy.losev.cs.core.graph.configs

enum class ExecutionStatus {
    PENDING,
    RUNNING,
    WAITING,      // ожидание внешнего события
    PAUSED,
    COMPLETED,
    FAILED,
    CANCELLED
}