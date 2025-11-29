package dmitriy.losev.cs.core.graph.context

/**
 * Прогресс выполнения асинхронной операции
 */
data class OperationProgress(
    val itemsProcessed: Int = 0,
    val totalItems: Int = 0,
    val lastUpdateTime: Long = System.currentTimeMillis()
) {
    val percentage: Int
        get() = if (totalItems > 0) (itemsProcessed * 100) / totalItems else 0
}