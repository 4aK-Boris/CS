package dmitriy.losev.cs.handlers.pulse

import dmitriy.losev.cs.dso.pulse.MarketItemDSO

interface MarketTradeHandler {

    suspend fun insertMarkerItems(markerItems: List<MarketItemDSO>)

    suspend fun removeMarkerItems()

    suspend fun getMarkerItems(buyMarket: String, sellMarket: String): List<MarketItemDSO>
}