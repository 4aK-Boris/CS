package dmitriy.losev.cs.dso.offers.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemPopularityDSO(

    @SerialName(value = "market")
    val market: String,

    @SerialName(value = "salesCount")
    val salesCount: Int,

    @SerialName(value = "marketItemUrl")
    val marketItemUrl: String?
)