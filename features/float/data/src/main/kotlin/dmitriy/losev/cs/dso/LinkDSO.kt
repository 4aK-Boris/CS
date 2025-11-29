package dmitriy.losev.cs.dso

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LinkDSO(

    @SerialName(value = "link")
    val link: String
)
