package dmitriy.losev.cs.dto.offers.response

data class OfferItemDTO(
    val itemName: ItemNameDTO,
    val imageUrl: String,
    val firstMarket: MarketDTO,
    val secondMarket: MarketDTO,
    val profit: Double,
    val profitPercent: Double,
    val isFavorite: Boolean,
    val inPurchaseList: Boolean,
    val inOldPurchaseList: Boolean?,
    val itemPopularity: List<ItemPopularityDTO>
)
