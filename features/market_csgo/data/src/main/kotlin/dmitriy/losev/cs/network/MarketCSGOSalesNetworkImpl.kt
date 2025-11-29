package dmitriy.losev.cs.network

import dmitriy.losev.cs.clients.MarketCSGOProxyClient
import dmitriy.losev.cs.dso.sales.ItemHistorySalesDSO
import dmitriy.losev.cs.dso.sales.ItemsIdDSO
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory(binds = [MarketCSGOSalesNetwork::class])
internal class MarketCSGOSalesNetworkImpl(@Provided private val marketCSGOProxyClient: MarketCSGOProxyClient) : MarketCSGOSalesNetwork {

    override suspend fun getItemsId(steamId: ULong): ItemsIdDSO {
        return marketCSGOProxyClient.get(
            steamId = steamId,
            handle = GET_ITEMS_ID_HANDLE,
            responseClazz = ItemsIdDSO::class,
        )
    }

    override suspend fun getItemHistorySales(steamId: ULong, itemId: Int): ItemHistorySalesDSO {
        return marketCSGOProxyClient.get(
            steamId = steamId,
            handle = GET_ITEM_HISTORY_SALES_HANDLE,
            responseClazz = ItemHistorySalesDSO::class,
        )
    }

    companion object {
        private const val GET_ITEMS_ID_HANDLE = "/api/v2/full-history/all.json"
        private const val GET_ITEM_HISTORY_SALES_HANDLE = "/api/v2/full-history/%d.json"
    }
}