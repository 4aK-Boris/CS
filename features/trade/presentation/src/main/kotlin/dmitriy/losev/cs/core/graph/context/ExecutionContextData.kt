package dmitriy.losev.cs.core.graph.context

import kotlinx.serialization.Serializable

@Serializable
data class ExecutionContextData(
    val variables: Map<String, String> = emptyMap(),
    val nodeResults: Map<String, NodeResultData> = emptyMap()
)