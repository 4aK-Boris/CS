package dmitriy.losev.cs.dso.offer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MarketActionDSO(

    @SerialName(value = "link")
    val link: String,

    @SerialName(value = "name")
    val name: String
)