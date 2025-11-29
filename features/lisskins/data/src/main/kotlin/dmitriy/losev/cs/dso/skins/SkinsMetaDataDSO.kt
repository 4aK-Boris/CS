package dmitriy.losev.cs.dso.skins

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SkinsMetaDataDSO(

    @SerialName(value = "per_page")
    val count: Int,

    @SerialName(value = "next_cursor")
    val nextCursor: String
)
