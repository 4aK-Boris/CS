package dmitriy.losev.cs.dso.offers.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemNameDSO(

    @SerialName(value = "marketHashName")
    val marketHashName: String,

    @SerialName(value = "isStatTrak")
    val isStatTrak: Boolean,

    @SerialName(value = "isSouvenir")
    val isSouvenir: Boolean,

    @SerialName(value = "itemTypeName")
    val itemTypeName: String,

    @SerialName(value = "skinName")
    val skinName: String
)
