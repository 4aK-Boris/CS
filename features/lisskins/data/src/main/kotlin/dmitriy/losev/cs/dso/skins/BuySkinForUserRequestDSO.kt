package dmitriy.losev.cs.dso.skins


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BuySkinForUserRequestDSO(

    @SerialName(value = "ids")
    val ids: List<Int>,

    @SerialName("partner")
    val partner: String,

    @SerialName("token")
    val token: String,

    @SerialName("max_price")
    val maxPrice: Int,

    @SerialName("custom_id")
    val customId: String,

    @SerialName("skip_unavailable")
    val skipUnavailable: Boolean
)