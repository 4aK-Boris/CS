package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dso.skins.SearchSkinsResponseDSO
import dmitriy.losev.cs.dto.skins.SearchSkinsResponseDTO
import org.koin.core.annotation.Factory

@Factory
class SearchSkinsResponseMapper(private val skinMapper: SkinMapper) {

    fun map(value: SearchSkinsResponseDSO): SearchSkinsResponseDTO {
        return SearchSkinsResponseDTO(
            skins = value.skins.map(transform = skinMapper::map),
            count = value.metaData.count,
            nextCursor = value.metaData.nextCursor
        )
    }
}