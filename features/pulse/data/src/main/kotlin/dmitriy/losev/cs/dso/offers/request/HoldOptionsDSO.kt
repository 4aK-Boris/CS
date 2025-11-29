package dmitriy.losev.cs.dso.offers.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HoldOptionsDSO(

    @SerialName(value = "minHold")
    val minHold: Int = 0,

    @SerialName(value = "maxHold")
    val maxHold: Int = 0,
)