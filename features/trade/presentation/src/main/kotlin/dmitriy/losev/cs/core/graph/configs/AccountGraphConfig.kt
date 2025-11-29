package dmitriy.losev.cs.core.graph.configs

import kotlinx.serialization.Serializable

@Serializable
data class AccountGraphConfig(
    val paramOverrides: Map<String, String> = emptyMap()
)