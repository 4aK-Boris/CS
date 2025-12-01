package dmitriy.losev.cs.repositories.pulse

import org.koin.core.annotation.Factory

@Factory(binds = [MarketTradeRepository::class])
class MarketTradeRepositoryImpl
    //@Provided private val marketTradeHandler: MarketTradeHandler,
    //private val marketItemMapper: MarketItemMapper
: MarketTradeRepository {

//    override suspend fun insertMarkerItems(markerItems: List<MarketItemDTO>) {
//        marketTradeHandler.insertMarkerItems(markerItems = markerItems.map(transform = marketItemMapper::map))
//    }
//
//    override suspend fun removeMarkerItems() {
//        marketTradeHandler.removeMarkerItems()
//    }
//
//    override suspend fun getMarkerItems(buyMarket: Market, sellMarket: Market): List<MarketItemDTO> {
//        return marketTradeHandler.getMarkerItems(buyMarket = buyMarket.name, sellMarket = sellMarket.name).map(transform = marketItemMapper::map)
//    }
}
