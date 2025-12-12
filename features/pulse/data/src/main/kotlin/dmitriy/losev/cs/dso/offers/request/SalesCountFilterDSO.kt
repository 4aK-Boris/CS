package dmitriy.losev.cs.dso.offers.request


import dmitriy.losev.cs.EMPTY_STRING
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SalesCountFilterDSO(

    @SerialName(value = "id")
    val id: Int = 0,

    @SerialName(value = "market")
    val market: String = EMPTY_STRING,

    @SerialName(value = "period")
    val period: String = EMPTY_STRING,

    @SerialName(value = "salesCount")
    val salesCount: Int = 0
)
