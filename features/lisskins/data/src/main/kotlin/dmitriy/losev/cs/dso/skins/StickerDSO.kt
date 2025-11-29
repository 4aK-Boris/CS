package dmitriy.losev.cs.dso.skins


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StickerDSO(

    @SerialName(value = "image")
    val image: String,

    @SerialName(value = "name")
    val name: String,

    @SerialName(value = "slot")
    val slot: Int,

    @SerialName(value = "wear")
    val wear: Int
)