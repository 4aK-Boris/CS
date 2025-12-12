package dmitriy.losev.cs.core

import dmitriy.losev.cs.pulse.Market
import java.time.Instant


class MarketPriceUpdater {

    private val marketPriceUpdaterMap = mutableMapOf<Pair<Market, Market>, Instant>()

    fun setMarketPriceUpdateAt(buyMarket: Market, sellMarket: Market) {
        marketPriceUpdaterMap[buyMarket to sellMarket] = currentTime
    }

    fun getMarketPriceUpdateAt(buyMarket: Market, sellMarket: Market): Instant {
        return marketPriceUpdaterMap[buyMarket to sellMarket] ?: Instant.MIN
    }

    private val currentTime: Instant
        get() = Instant.now()
}
