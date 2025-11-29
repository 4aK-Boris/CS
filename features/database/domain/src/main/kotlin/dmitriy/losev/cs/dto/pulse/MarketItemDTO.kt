package dmitriy.losev.cs.dto.pulse

import dmitriy.losev.cs.pulse.Market
import java.time.LocalDateTime

data class MarketItemDTO(
    val itemName: String,
    val minBuyPrice: Double,
    val maxSellPrice: Double,
    val buyMarket: Market,
    val sellMarket: Market,
    val offersCount: Int,
    val profit: Double,
    val lastUpdatedInBuyMarket: LocalDateTime,
    val lastUpdatedInSellMarket: LocalDateTime,
    val firstAddition: LocalDateTime,
)
