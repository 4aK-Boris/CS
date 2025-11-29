package dmitriy.losev.cs.dso.offers.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OfferItemDSO(

    @SerialName(value = "itemName")
    val itemName: ItemNameDSO,

    @SerialName(value = "imageUrl")
    val imageUrl: String,

    @SerialName(value = "firstMarket")
    val firstMarket: MarketDSO,

    @SerialName(value = "secondMarket")
    val secondMarket: MarketDSO,

    @SerialName(value = "profit")
    val profit: Double,

    @SerialName(value = "profitPercent")
    val profitPercent: Double,

    @SerialName(value = "isFavorite")
    val isFavorite: Boolean,

    @SerialName(value = "inPurchaseList")
    val inPurchaseList: Boolean,

    @SerialName(value = "inOldPurchaseList")
    val inOldPurchaseList: Boolean?,

    @SerialName(value = "itemPopularity")
    val itemPopularity: List<ItemPopularityDSO>,
)