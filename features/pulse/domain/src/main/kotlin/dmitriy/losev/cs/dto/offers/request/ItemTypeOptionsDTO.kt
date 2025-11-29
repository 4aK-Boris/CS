package dmitriy.losev.cs.dto.offers.request

data class ItemTypeOptionsDTO(
    val itemTypes: String? = null,
    val itemQualities: String? = null,
    val isStatTrack: Boolean? = null,
    val isSouvenir: Boolean? = null,
    val isSticker: Boolean? = null,
    val isGraffiti: Boolean? = null,
    val holdOptions: HoldOptionsDTO? = HoldOptionsDTO(),
    val indicationOptions: IndicationOptionsDTO = IndicationOptionsDTO(),
    val isOverstock: Boolean? = null,
    val displaySoldOutItems: Boolean = false,
    val displayOnlyOverridenItems: Boolean = false,
    val firstMarketTime: Long? = null,
    val secondMarketTime: Long? = null
)