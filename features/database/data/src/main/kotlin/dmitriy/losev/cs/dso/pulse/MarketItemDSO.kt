package dmitriy.losev.cs.dso.pulse

import java.time.LocalDateTime

internal data class MarketItemDSO(
    val itemName: String,
    val minBuyPrice: Double,
    val maxSellPrice: Double,
    val buyMarket: String,
    val sellMarket: String,
    val offersCount: Int,
    val profit: Double,
    val lastUpdatedInBuyMarket: LocalDateTime,
    val lastUpdatedInSellMarket: LocalDateTime,
    val firstAddition: LocalDateTime,
)
