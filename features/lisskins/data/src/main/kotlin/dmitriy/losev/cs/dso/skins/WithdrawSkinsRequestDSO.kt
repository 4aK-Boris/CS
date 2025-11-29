package dmitriy.losev.cs.dso.skins


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WithdrawSkinsRequestDSO(

    @SerialName(value = "custom_id")
    val customId: String,

    @SerialName(value = "purchase_id")
    val purchaseId: Int,

    @SerialName(value = "partner")
    val partner: String,

    @SerialName(value = "token")
    val token: String
)