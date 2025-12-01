package dmitriy.losev.cs.dso

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TradeOfferAcceptResultDSO(

    @SerialName(value = "tradeid")
    val tradeId: String? = null,

    @SerialName(value = "needs_mobile_confirmation")
    val needsMobileConfirmation: Boolean = false,

    @SerialName(value = "strError")
    val strError: String? = null
)
