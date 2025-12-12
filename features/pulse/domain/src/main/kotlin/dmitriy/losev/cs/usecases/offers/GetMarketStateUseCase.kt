package dmitriy.losev.cs.usecases.offers

import dmitriy.losev.cs.pulse.Market
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory

@Factory
class GetMarketStateUseCase: BaseUseCase {

    operator fun invoke(buyMarket: Market, sellMarket: Market): Result<Boolean> = runCatching {
        false
    }
}
