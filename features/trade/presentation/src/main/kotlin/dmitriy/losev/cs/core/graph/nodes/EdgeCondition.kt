package dmitriy.losev.cs.core.graph.nodes

import dmitriy.losev.cs.core.graph.context.ExecutionContext

sealed interface EdgeCondition {
    object Always : EdgeCondition
    data class OnSuccess(val resultKey: String? = null) : EdgeCondition
    data class OnFailure(val retryCount: Int = 0) : EdgeCondition
    data class When(val predicate: (ExecutionContext) -> Boolean) : EdgeCondition
}