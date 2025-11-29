package dmitriy.losev.cs.dso.offers.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateTimeFilterDSO(

    @SerialName(value = "minTime")
    val minTime: Long? = null,

    @SerialName(value = "maxTime")
    val maxTime: Long? = null
)