package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.dto.ListingInfoDTO
import dmitriy.losev.cs.dto.item.ItemInfoDTO
import dmitriy.losev.cs.dto.item.ItemLinkDTO
import dmitriy.losev.cs.handlers.ItemHandler
import dmitriy.losev.cs.mappers.item.ItemInfoMapper
import dmitriy.losev.cs.mappers.item.ItemLinkMapper
import dmitriy.losev.cs.mappers.item.ItemSaleOfferMapper
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory(binds = [ItemRepository::class])
internal class ItemRepositoryImpl(
    @Provided private val itemHandler: ItemHandler,
    private val itemInfoMapper: ItemInfoMapper,
    private val itemSaleOfferMapper: ItemSaleOfferMapper,
    private val itemLinkMapper: ItemLinkMapper
): ItemRepository {

    override suspend fun insertItemInfo(itemInfo: ItemInfoDTO): String? {
        return itemHandler.insertItemInfo(itemInfo = itemInfoMapper.map(value = itemInfo))
    }

    override suspend fun getItemsInfo(delay: Long, count: Int): List<ItemInfoDTO> {
        return itemHandler.getItemsInfo(delay, count).map(transform = itemInfoMapper::map)
    }

    override suspend fun insertItemSaleOffers(items: List<Triple<ULong, ULong, ListingInfoDTO>>): List<ItemLinkDTO> {
        val itemSaleOffersDSO = items.map(transform = itemSaleOfferMapper::map)
        return itemHandler.insertItemSaleOffers(itemSaleOffers = itemSaleOffersDSO).map(transform = itemLinkMapper::map)
    }
}
