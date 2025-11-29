package dmitriy.losev.cs.core.graph.configs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface ItemSelectorConfig {
    @Serializable
    @SerialName("by_name")
    data class ByName(val marketHashName: String) : ItemSelectorConfig

    @Serializable
    @SerialName("by_float")
    data class ByFloat(
        val marketHashName: String,
        val minFloat: Float = 0f,
        val maxFloat: Float = 1f
    ) : ItemSelectorConfig

    @Serializable
    @SerialName("from_node")
    data class FromNode(val nodeKey: String) : ItemSelectorConfig
}