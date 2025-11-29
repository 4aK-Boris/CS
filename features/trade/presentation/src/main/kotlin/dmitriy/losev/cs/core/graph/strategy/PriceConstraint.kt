package dmitriy.losev.cs.core.graph.strategy

import dmitriy.losev.cs.core.graph.context.ExecutionContext
import dmitriy.losev.cs.core.market.Market

sealed interface PriceConstraint {
    data class Fixed(val maxCents: Long) : PriceConstraint
    data class MarketPercent(val percent: Double, val market: Market) : PriceConstraint
    data class Dynamic(val calculator: (ExecutionContext) -> Long) : PriceConstraint
}