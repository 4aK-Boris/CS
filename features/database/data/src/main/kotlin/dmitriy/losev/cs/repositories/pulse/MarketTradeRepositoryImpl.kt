package dmitriy.losev.cs.repositories.pulse

import dmitriy.losev.cs.dto.pulse.MarketItemDTO
import dmitriy.losev.cs.handlers.pulse.MarketTradeHandler
import dmitriy.losev.cs.mappers.pulse.MarketItemMapper
import org.koin.core.annotation.Factory

@Factory(binds = [MarketTradeRepository::class])
internal class MarketTradeRepositoryImpl(
    private val marketTradeHandler: MarketTradeHandler,
    private val marketItemMapper: MarketItemMapper
) : MarketTradeRepository {

    override suspend fun upsertMarkerItems(markerItems: List<MarketItemDTO>) {
        return marketTradeHandler.upsertMarkerItems(markerItems = markerItems.map(transform = marketItemMapper::map))
    }
}
