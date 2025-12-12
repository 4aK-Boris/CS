package dmitriy.losev.cs.dso.offers.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IndicationOptionsDSO(

    @SerialName(value = "isEnabled")
    val isEnabled: Boolean = false,

    @SerialName(value = "colorIndicators")
    val colorIndicators: List<String> = emptyList(),
)
