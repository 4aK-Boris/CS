package dmitriy.losev.cs.dto.pulse

import dmitriy.losev.cs.pulse.Market
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class MarketItemDTO(
    val marketHashName: String,
    val market: Market,
    val minPrice: Int? = null,
    val buyOrderPrice: Int? = null,
    val tradeOnPrice: Int? = null,
    val weeklySalesCount: Int,
    val createdAt: Instant
)
