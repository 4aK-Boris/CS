package dmitriy.losev.cs.dso

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemInfoFloatDSO(

    @SerialName("iteminfo")
    val itemInfoFloat: ItemFloatDSO
)