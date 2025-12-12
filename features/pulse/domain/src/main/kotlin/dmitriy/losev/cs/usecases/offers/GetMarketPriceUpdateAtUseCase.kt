package dmitriy.losev.cs.usecases.offers

import dmitriy.losev.cs.pulse.Market
import dmitriy.losev.cs.repositories.PulseRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Factory
@OptIn(ExperimentalTime::class)
class GetMarketPriceUpdateAtUseCase(@Provided private val pulseRepository: PulseRepository) : BaseUseCase {

    suspend operator fun invoke(buyMarket: Market, sellMarket: Market): Result<Instant> = runCatching {
        pulseRepository.getMarketPriceUpdateAt(buyMarket, sellMarket)
    }
}
