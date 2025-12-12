package dmitriy.losev.cs.dto.offers

import dmitriy.losev.cs.core.PriceType
import dmitriy.losev.cs.pulse.Market

data class MarketConfig(
    val minProfit: Int,
    val buyMarket: Market,
    val sellMarket: Market,
    val buyMarketPriceType: PriceType,
    val sellMarketPriceType: PriceType,
    val minPrice: Double,
    val maxPrice: Double
)
