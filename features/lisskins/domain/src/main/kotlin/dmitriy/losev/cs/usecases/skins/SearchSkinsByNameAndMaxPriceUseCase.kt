package dmitriy.losev.cs.usecases.skins

import dmitriy.losev.cs.dto.skins.SearchSkinsRequestDTO
import dmitriy.losev.cs.dto.skins.SearchSkinsResponseDTO
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory

@Factory
class SearchSkinsByNameAndMaxPriceUseCase(private val searchSkinsUseCase: SearchSkinsUseCase) : BaseUseCase {

    suspend operator fun invoke(itemName: String, maxPrice: Double): Result<SearchSkinsResponseDTO> {
        return SearchSkinsRequestDTO(itemNames = listOf(itemName), priceTo = maxPrice).let { searchSkinsRequest ->
            searchSkinsUseCase.invoke(searchSkinsRequest)
        }
    }
}