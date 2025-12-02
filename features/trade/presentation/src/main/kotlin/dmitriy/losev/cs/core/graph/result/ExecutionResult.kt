package dmitriy.losev.cs.core.graph.result

/**
 * Результат выполнения графа
 */
sealed interface ExecutionResult {
    val steamId: Long
    val executedNodes: List<String>

    data class Success(
        override val steamId: Long,
        override val executedNodes: List<String>
    ) : ExecutionResult

    data class Failed(
        override val steamId: Long,
        val failedAtNode: String,
        override val executedNodes: List<String>,
        val error: String
    ) : ExecutionResult
}