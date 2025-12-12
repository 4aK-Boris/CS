package dmitriy.losev.cs.dso.offers.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemTypeOptionsDSO(

    @SerialName(value = "itemTypes")
    val itemTypes: String? = null,

    @SerialName(value = "itemQualities")
    val itemQualities: String? = null,

    @SerialName(value = "isStatTrack")
    val isStatTrack: Boolean? = null,

    @SerialName(value = "isSouvenir")
    val isSouvenir: Boolean? = null,

    @SerialName(value = "isSticker")
    val isSticker: Boolean? = null,

    @SerialName(value = "isGraffiti")
    val isGraffiti: Boolean? = null,

    @SerialName(value = "holdOptions")
    val holdOptions: HoldOptionsDSO? = HoldOptionsDSO(),

    @SerialName(value = "indicationOptions")
    val indicationOptions: IndicationOptionsDSO = IndicationOptionsDSO(),

    @SerialName(value = "isOverstock")
    val isOverstock: Boolean? = null,

    @SerialName(value = "displaySoldOutItems")
    val displaySoldOutItems: Boolean = false,

    @SerialName(value = "displayOnlyOverridenItems")
    val displayOnlyOverridenItems: Boolean = false,

    @SerialName(value = "firstMarketTime")
    val firstMarketTime: Long? = null,

    @SerialName(value = "secondMarketTime")
    val secondMarketTime: Long? = null
)
