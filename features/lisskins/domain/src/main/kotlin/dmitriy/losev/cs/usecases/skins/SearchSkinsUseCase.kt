package dmitriy.losev.cs.usecases.skins

import dmitriy.losev.cs.dto.skins.SearchSkinsRequestDTO
import dmitriy.losev.cs.dto.skins.SearchSkinsResponseDTO
import dmitriy.losev.cs.repositories.LisSkinsSkinsRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class SearchSkinsUseCase(@Provided private val lisSkinsSkinsRepository: LisSkinsSkinsRepository) : BaseUseCase {

    suspend operator fun invoke(searchSkinsRequest: SearchSkinsRequestDTO): Result<SearchSkinsResponseDTO> = runCatching {
        lisSkinsSkinsRepository.searchSkins(searchSkinsRequest)
    }
}