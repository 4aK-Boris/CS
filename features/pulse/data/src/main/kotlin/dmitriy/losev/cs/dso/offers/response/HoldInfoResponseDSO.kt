package dmitriy.losev.cs.dso.offers.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HoldInfoResponseDSO(

    @SerialName(value = "minHold")
    val minHold: Int,

    @SerialName(value = "maxHold")
    val maxHold: Int,
)
