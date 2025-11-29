package dmitriy.losev.cs.repositories.pulse

import dmitriy.losev.cs.dto.pulse.MarketItemDTO
import dmitriy.losev.cs.pulse.Market

interface MarketTradeRepository {

    suspend fun insertMarkerItems(markerItems: List<MarketItemDTO>)

    suspend fun removeMarkerItems()

    suspend fun getMarkerItems(buyMarket: Market, sellMarket: Market): List<MarketItemDTO>
}