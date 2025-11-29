package dmitriy.losev.cs.mappers.offers.response

import dmitriy.losev.cs.dso.offers.response.OfferItemDSO
import dmitriy.losev.cs.dto.offers.response.OfferItemDTO
import org.koin.core.annotation.Factory

@Factory
class OfferItemMapper(
    private val itemNameMapper: ItemNameMapper,
    private val marketMapper: MarketMapper,
    private val itemPopularityMapper: ItemPopularityMapper
) {

    fun map(value: OfferItemDSO): OfferItemDTO {
        return OfferItemDTO(
            itemName = itemNameMapper.map(value = value.itemName),
            imageUrl = value.imageUrl,
            firstMarket = marketMapper.map(value = value.firstMarket),
            secondMarket = marketMapper.map(value = value.secondMarket),
            profit = value.profit,
            profitPercent = value.profitPercent,
            isFavorite = value.isFavorite,
            inPurchaseList = value.inPurchaseList,
            inOldPurchaseList = value.inOldPurchaseList,
            itemPopularity = value.itemPopularity.map(transform = itemPopularityMapper::map)
        )
    }
}