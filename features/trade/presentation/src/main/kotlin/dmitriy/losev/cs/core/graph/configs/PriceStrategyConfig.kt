package dmitriy.losev.cs.core.graph.configs

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
sealed interface PriceStrategyConfig {
    @Serializable
    @SerialName("fixed")
    data class Fixed(val priceCents: Long) : PriceStrategyConfig

    @Serializable
    @SerialName("market_plus")
    data class MarketPlus(val percent: Double) : PriceStrategyConfig

    @Serializable
    @SerialName("undercut")
    data class Undercut(val byAmount: Long = 1) : PriceStrategyConfig
}