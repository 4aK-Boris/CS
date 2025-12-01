package dmitriy.losev.cs.handlers

import dmitriy.losev.cs.dso.item.ItemFloatDSO
import dmitriy.losev.cs.dso.item.ItemInfoDSO
import dmitriy.losev.cs.dso.item.ItemLinkDSO
import dmitriy.losev.cs.dso.item.ItemSaleOfferDSO

internal interface ItemHandler {

    suspend fun insertItemInfo(itemInfo: ItemInfoDSO): String?

    suspend fun getItemsInfo(delay: Long, count: Int): List<ItemInfoDSO>

    suspend fun insertItemSaleOffers(itemSaleOffers: List<ItemSaleOfferDSO>): List<ItemLinkDSO>

    suspend fun insertItemsFloat(itemsFloat: List<ItemFloatDSO>)

    suspend fun findListingIdForRarePattern(itemFloat: ItemFloatDSO): ULong?
}
