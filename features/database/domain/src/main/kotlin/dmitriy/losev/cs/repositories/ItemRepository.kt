package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.dto.item.ItemInfoDTO
import dmitriy.losev.cs.dto.item.ItemLinkDTO
import dmitriy.losev.cs.dto.ListingInfoDTO

interface ItemRepository {

    suspend fun insertItemInfo(itemInfo: ItemInfoDTO): String?

    suspend fun getItemsInfo(delay: Long, count: Int): List<ItemInfoDTO>

    suspend fun insertItemSaleOffers(items: List<Triple<ULong, ULong, ListingInfoDTO>>): List<ItemLinkDTO>
}