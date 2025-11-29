package dmitriy.losev.cs.core.graph.nodes

import dmitriy.losev.cs.core.graph.configs.NodeConfig
import dmitriy.losev.cs.core.graph.configs.NodeType
import kotlinx.serialization.Serializable

@Serializable
data class NodeDefinition(
    val key: String,
    val type: NodeType,
    val config: NodeConfig
)