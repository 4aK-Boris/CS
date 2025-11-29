package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dso.skins.SearchSkinsRequestDSO
import dmitriy.losev.cs.dto.skins.SearchSkinsRequestDTO
import org.koin.core.annotation.Factory

@Factory
class SearchSkinsRequestMapper {

    fun map(value: SearchSkinsRequestDTO): SearchSkinsRequestDSO {
        return SearchSkinsRequestDSO(
            cursor = value.cursor,
            game = value.game,
            floatFrom = value.floatFrom,
            floatTo = value.floatTo,
            itemNames = value.itemNames,
            onlyUnlocked = value.onlyUnlocked,
            priceFrom = value.priceFrom,
            priceTo = value.priceTo,
            sortBy = value.sortBy.title,
            unlockDays = value.unlockDays
        )
    }
}