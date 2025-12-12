package dmitriy.losev.cs.dso.offers.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfitPercentFilterDSO(

    @SerialName(value = "minValue")
    val minValue: Int? = null,

    @SerialName(value = "maxValue")
    val maxValue: Int? = null
)
