package dmitriy.losev.cs.dso.skins


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Serializable
data class BuyOrWithdrawSkinForUserDataDSO(

    @SerialName("purchase_id")
    val purchaseId: Int,

    @SerialName("steam_id")
    val steamId: String,

    @SerialName("created_at")
    val createdAt: Instant,

    @SerialName("custom_id")
    val customId: String? = null,

    @SerialName("skins")
    val skins: List<TradeSkinDSO>
)