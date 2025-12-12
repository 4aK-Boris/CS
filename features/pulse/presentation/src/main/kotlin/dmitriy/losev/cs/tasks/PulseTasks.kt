package dmitriy.losev.cs.tasks

import dmitriy.losev.cs.core.PriceType
import dmitriy.losev.cs.dto.offers.MarketConfig
import dmitriy.losev.cs.pulse.Market
import dmitriy.losev.cs.usecases.offers.GetAndSaveOffersUseCase
import org.koin.core.annotation.Factory

@Factory
class PulseTasks(private val getAndSaveOffersUseCase: GetAndSaveOffersUseCase) {

    suspend fun getAndSaveOffersForAllMarketsPairTask(): List<Result<Unit>> {
        return marketConfigs.map { marketConfig ->
            getAndSaveOffersUseCase.invoke(marketConfig)
        }
    }

    private val marketConfigs: List<MarketConfig> = listOf(
        MarketConfig(
            minProfit = 30,
            buyMarket = Market.LIS_SKINS,
            sellMarket = Market.CS_MARKET,
            buyMarketPriceType = PriceType.SELL,
            sellMarketPriceType = PriceType.SELL,
            minPrice = 0.01,
            maxPrice = 100.0
        ),
        MarketConfig(
            minProfit = 20,
            buyMarket = Market.CS_MARKET,
            sellMarket = Market.CS_FLOAT,
            buyMarketPriceType = PriceType.BUY,
            sellMarketPriceType = PriceType.SELL,
            minPrice = 0.5,
            maxPrice = 100.0
        ),
        MarketConfig(
            minProfit = 10,
            buyMarket = Market.CS_FLOAT,
            sellMarket = Market.CS_MARKET,
            buyMarketPriceType = PriceType.SELL,
            sellMarketPriceType = PriceType.SELL,
            minPrice = 0.01,
            maxPrice = 100.0
        ),
    )
}
