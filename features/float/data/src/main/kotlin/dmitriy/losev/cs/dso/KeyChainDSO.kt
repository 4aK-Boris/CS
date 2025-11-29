package dmitriy.losev.cs.dso

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KeyChainDSO(

    @SerialName(value = "slot")
    val slot: Int,

    @SerialName(value = "sticker_id")
    val stickerId: Int,

    @SerialName(value = "pattern")
    val pattern: Int
)