package dmitriy.losev.cs.dso.pulse

import java.time.Instant

internal data class MarketItemDSO(
    val marketHashName: String,
    val market: String,
    val minPrice: Int?,
    val buyOrderPrice: Int?,
    val tradeOnPrice: Int?,
    val weeklySalesCount: Int,
    val createdAt: Instant
)
