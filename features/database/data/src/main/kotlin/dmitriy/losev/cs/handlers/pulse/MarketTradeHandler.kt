package dmitriy.losev.cs.handlers.pulse

import dmitriy.losev.cs.dso.pulse.MarketItemDSO

internal interface MarketTradeHandler {

    suspend fun upsertMarkerItems(markerItems: List<MarketItemDSO>)
}
