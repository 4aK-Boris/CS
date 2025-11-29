package dmitriy.losev.cs.core.graph.configs

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
sealed interface PriceConstraintConfig {
    @Serializable
    @SerialName("fixed")
    data class Fixed(val maxCents: Long) : PriceConstraintConfig

    @Serializable
    @SerialName("market_percent")
    data class MarketPercent(
        val percent: Double,
        val referenceMarket: String
    ) : PriceConstraintConfig
}