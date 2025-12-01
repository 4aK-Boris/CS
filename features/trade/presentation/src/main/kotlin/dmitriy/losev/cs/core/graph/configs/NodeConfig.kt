package dmitriy.losev.cs.core.graph.configs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Конфиги для JSONB
@Serializable
sealed interface NodeConfig {
    @Serializable
    @SerialName("start")
    data object Start : NodeConfig

    @Serializable
    @SerialName("end")
    data object End : NodeConfig

    @Serializable
    @SerialName("buy")
    data class Buy(
        val market: String,
        val itemSelector: ItemSelectorConfig,
        val maxPrice: PriceConstraintConfig,
        val timeout: Long = 300_000  // 5 минут по умолчанию
    ) : NodeConfig

    @Serializable
    @SerialName("sell")
    data class Sell(
        val market: String,
        val itemFromNode: String,
        val priceStrategy: PriceStrategyConfig,
        val autoRelist: Boolean = true
    ) : NodeConfig

    @Serializable
    @SerialName("wait")
    data class Wait(
        val condition: WaitConditionConfig
    ) : NodeConfig

    @Serializable
    @SerialName("condition")
    data class Condition(
        val expression: String,  // SpEL или простой DSL
        val trueNodeKey: String,
        val falseNodeKey: String
    ) : NodeConfig

    @Serializable
    @SerialName("transfer")
    data class Transfer(
        val itemFromNode: String,
        val toAccountId: String,
        val message: String = ""
    ) : NodeConfig
}