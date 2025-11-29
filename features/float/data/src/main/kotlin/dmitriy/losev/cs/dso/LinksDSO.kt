package dmitriy.losev.cs.dso

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LinksDSO(

    @SerialName(value = "links")
    val links: List<LinkDSO>
)
