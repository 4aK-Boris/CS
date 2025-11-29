package dmitriy.losev.cs.dso.skins


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Serializable
data class TradeSkinDSO(

    @SerialName(value = "id")
    val id: Int,

    @SerialName(value = "name")
    val name: String,

    @SerialName(value = "price")
    val price: Double,

    @SerialName(value = "status")
    val status: String,

    @SerialName(value = "return_reason")
    val returnReason: String? = null,

    @SerialName(value = "return_charged_commission")
    val returnChargedCommission: Double? = null,

    @SerialName(value = "error")
    val error: String? = null,

    @SerialName(value = "steam_trade_offer_id")
    val steamTradeOfferId: String? = null,

    @SerialName(value = "steam_trade_offer_created_at")
    val steamTradeOfferCreatedAt: Instant? = null,

    @SerialName(value = "steam_trade_offer_expiry_at")
    val steamTradeOfferExpiryAt: Instant? = null,

    @SerialName(value = "steam_trade_offer_finished_at")
    val steamTradeOfferFinishedAt: Instant? = null
)