package dmitriy.losev.cs.dso.offer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListingAssetDSO(

    @SerialName(value = "currency")
    val currency: Int,

    @SerialName(value = "appid")
    val appId: Int,

    @SerialName(value = "contextid")
    val contextId: String,

    @SerialName(value = "id")
    val id: String,

    @SerialName(value = "amount")
    val amount: String,

    @SerialName(value = "market_actions")
    val marketActions: List<MarketActionDSO>
)