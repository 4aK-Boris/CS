package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.dto.sales.ItemHistorySalesDTO
import dmitriy.losev.cs.dto.sales.ItemsIdDTO

interface MarketCSGOSalesRepository {

    suspend fun getItemsId(steamId: ULong): ItemsIdDTO

    suspend fun getItemHistorySales(steamId: ULong, itemId: Int): ItemHistorySalesDTO
}