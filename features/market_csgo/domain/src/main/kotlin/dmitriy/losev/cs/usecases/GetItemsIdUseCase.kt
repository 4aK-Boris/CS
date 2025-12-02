package dmitriy.losev.cs.usecases

import dmitriy.losev.cs.dto.sales.ItemsIdDTO
import dmitriy.losev.cs.repositories.MarketCSGOSalesRepository
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetItemsIdUseCase(@Provided private val marketCSGOSalesRepository: MarketCSGOSalesRepository) : BaseUseCase {

    suspend operator fun invoke(steamId: Long): Result<ItemsIdDTO> = runCatching {
        marketCSGOSalesRepository.getItemsId(steamId)
    }
}