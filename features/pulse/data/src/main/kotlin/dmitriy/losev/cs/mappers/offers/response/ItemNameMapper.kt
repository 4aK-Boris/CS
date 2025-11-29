package dmitriy.losev.cs.mappers.offers.response

import dmitriy.losev.cs.dso.offers.response.ItemNameDSO
import dmitriy.losev.cs.dto.offers.response.ItemNameDTO
import org.koin.core.annotation.Factory

@Factory
class ItemNameMapper {

    fun map(value: ItemNameDSO): ItemNameDTO {
        return ItemNameDTO(
            marketHashName = value.marketHashName,
            isStatTrak = value.isStatTrak,
            isSouvenir = value.isSouvenir,
            itemTypeName = value.itemTypeName,
            skinName = value.skinName
        )
    }
}