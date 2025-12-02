package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.dto.sales.ItemHistorySalesDTO
import dmitriy.losev.cs.dto.sales.ItemsIdDTO
import dmitriy.losev.cs.mappers.sales.ItemHistorySalesMapper
import dmitriy.losev.cs.mappers.sales.ItemsIdMapper
import dmitriy.losev.cs.network.MarketCSGOSalesNetwork
import org.koin.core.annotation.Factory

@Factory(binds = [MarketCSGOSalesRepository::class])
internal class MarketCSGOSalesRepositoryImpl(
    private val marketCSGOSalesNetwork: MarketCSGOSalesNetwork,
    private val itemsIdMapper: ItemsIdMapper,
    private val itemHistorySalesMapper: ItemHistorySalesMapper
) : MarketCSGOSalesRepository {

    override suspend fun getItemsId(steamId: Long): ItemsIdDTO {
        return itemsIdMapper.map(value = marketCSGOSalesNetwork.getItemsId(steamId))
    }

    override suspend fun getItemHistorySales(steamId: Long, itemId: Int): ItemHistorySalesDTO {
        return itemHistorySalesMapper.map(value = marketCSGOSalesNetwork.getItemHistorySales(steamId, itemId))
    }
}