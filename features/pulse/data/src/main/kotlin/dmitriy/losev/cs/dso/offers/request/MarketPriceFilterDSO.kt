package dmitriy.losev.cs.dso.offers.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MarketPriceFilterDSO(

    @SerialName(value = "minValue")
    val minValue: Double? = null,

    @SerialName(value = "maxValue")
    val maxValue: Double? = null
)
