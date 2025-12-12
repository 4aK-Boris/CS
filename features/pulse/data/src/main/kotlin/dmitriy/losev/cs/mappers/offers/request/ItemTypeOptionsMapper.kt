package dmitriy.losev.cs.mappers.offers.request

import dmitriy.losev.cs.dso.offers.request.ItemTypeOptionsDSO
import dmitriy.losev.cs.dto.offers.request.ItemTypeOptionsDTO
import org.koin.core.annotation.Factory

@Factory
class ItemTypeOptionsMapper(
    private val holdOptionsMapper: HoldOptionsMapper,
    private val indicationOptionsMapper: IndicationOptionsMapper
) {

    fun map(value: ItemTypeOptionsDTO): ItemTypeOptionsDSO {
        return ItemTypeOptionsDSO(
            itemTypes = value.itemTypes,
            itemQualities = value.itemQualities,
            isStatTrack = value.isStatTrack,
            isSouvenir = value.isSouvenir,
            isSticker = value.isSticker,
            isGraffiti = value.isGraffiti,
            holdOptions = value.holdOptions?.let(block = holdOptionsMapper::map),
            indicationOptions = indicationOptionsMapper.map(value = value.indicationOptions),
            isOverstock = value.isOverstock,
            displaySoldOutItems = value.displaySoldOutItems,
            displayOnlyOverridenItems = value.displayOnlyOverridenItems,
            firstMarketTime = value.firstMarketTime,
            secondMarketTime = value.secondMarketTime
        )
    }
}
