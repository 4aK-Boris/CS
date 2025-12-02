package dmitriy.losev.cs.usecases

import dmitriy.losev.cs.dto.sales.ItemHistorySalesDTO
import dmitriy.losev.cs.repositories.MarketCSGOSalesRepository
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetItemHistorySalesUseCase(@Provided private val marketCSGOSalesRepository: MarketCSGOSalesRepository) : BaseUseCase {

    suspend operator fun invoke(steamId: Long, itemId: Int): Result<ItemHistorySalesDTO> = runCatching {
        marketCSGOSalesRepository.getItemHistorySales(steamId, itemId)
    }
}