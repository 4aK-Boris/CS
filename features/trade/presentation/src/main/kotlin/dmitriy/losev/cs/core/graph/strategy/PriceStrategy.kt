package dmitriy.losev.cs.core.graph.strategy

sealed interface PriceStrategy {
    data class Fixed(val priceCents: Long) : PriceStrategy
    data class MarketPlus(val percent: Double) : PriceStrategy
    data class Undercut(val byAmount: Long = 1) : PriceStrategy
}