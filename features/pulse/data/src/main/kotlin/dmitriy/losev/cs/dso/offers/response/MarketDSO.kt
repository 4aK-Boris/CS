package dmitriy.losev.cs.dso.offers.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MarketDSO(

    @SerialName(value = "id")
    val id: Int,

    @SerialName(value = "price")
    val price: Double,

    @SerialName(value = "realPrice")
    val realPrice: Double,

    @SerialName(value = "overriddenPrice")
    val overriddenPrice: Double? = null,

    @SerialName(value = "realPriceCurrency")
    val realPriceCurrency: String,

    @SerialName(value = "bestOfferCount")
    val bestOfferCount: Int? = null,

    @SerialName(value = "totalOffersCount")
    val totalOffersCount: Int,

    @SerialName(value = "historyUpdateTime")
    val historyUpdateTime: Long,

    @SerialName(value = "offersUpdateTime")
    val offersUpdateTime: Long,

    @SerialName(value = "overstockInfo")
    val overstockInfo: String? = null,

    @SerialName(value = "holdInfoResponse")
    val holdInfoResponse: HoldInfoResponseDSO? = null,

    @SerialName(value = "soldOutTime")
    val soldOutTime: Long? = null,
)