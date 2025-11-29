package dmitriy.losev.cs.usecases.pulse

import dmitriy.losev.cs.repositories.pulse.MarketTradeRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class RemoveOldMarketItemsUseCase(@Provided private val marketTradeRepository: MarketTradeRepository): BaseUseCase {

    suspend operator fun invoke(): Result<Unit> = runCatching {
        marketTradeRepository.removeMarkerItems()
    }
}