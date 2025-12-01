package dmitriy.losev.cs.core.graph.configs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface ConditionConfig {
    @Serializable
    @SerialName("always")
    data object Always : ConditionConfig

    @Serializable
    @SerialName("on_success")
    data class OnSuccess(val resultKey: String? = null) : ConditionConfig

    @Serializable
    @SerialName("on_failure")
    data class OnFailure(val retryCount: Int = 0) : ConditionConfig

    @Serializable
    @SerialName("custom")
    data class Custom(val expression: String) : ConditionConfig
}