package dmitriy.losev.cs.network

import dmitriy.losev.cs.dso.sales.ItemHistorySalesDSO
import dmitriy.losev.cs.dso.sales.ItemsIdDSO

internal interface MarketCSGOSalesNetwork {

    suspend fun getItemsId(steamId: Long): ItemsIdDSO

    suspend fun getItemHistorySales(steamId: Long, itemId: Int): ItemHistorySalesDSO
}