package dmitriy.losev.cs.repositories.pulse

import dmitriy.losev.cs.dto.pulse.MarketItemDTO

interface MarketTradeRepository {

    suspend fun upsertMarkerItems(markerItems: List<MarketItemDTO>)
}
