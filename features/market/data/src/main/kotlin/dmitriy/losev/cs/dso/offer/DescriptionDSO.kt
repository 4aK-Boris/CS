package dmitriy.losev.cs.dso.offer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DescriptionDSO(

    @SerialName(value = "type")
    val type: String,

    @SerialName(value = "value")
    val value: String,

    @SerialName(value = "name")
    val name: String,

    @SerialName(value = "color")
    val color: String? = null
)