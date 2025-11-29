package dmitriy.losev.cs.core.graph.result

/**
 * Результат выполнения графа
 */
sealed interface ExecutionResult {
    val steamId: ULong
    val executedNodes: List<String>

    data class Success(
        override val steamId: ULong,
        override val executedNodes: List<String>
    ) : ExecutionResult

    data class Failed(
        override val steamId: ULong,
        val failedAtNode: String,
        override val executedNodes: List<String>,
        val error: String
    ) : ExecutionResult
}