package dmitriy.losev.cs.dso.skins

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchSkinsResponseDSO(

    @SerialName(value = "data")
    val skins: List<SkinDSO>,

    @SerialName(value = "meta")
    val metaData: SkinsMetaDataDSO
)