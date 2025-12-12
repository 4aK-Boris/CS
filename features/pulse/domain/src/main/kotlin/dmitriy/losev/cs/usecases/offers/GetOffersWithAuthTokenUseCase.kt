package dmitriy.losev.cs.usecases.offers

import dmitriy.losev.cs.Context
import dmitriy.losev.cs.core.PriceType
import dmitriy.losev.cs.dto.offers.MarketConfig
import dmitriy.losev.cs.dto.offers.request.MarketOptionsDTO
import dmitriy.losev.cs.dto.offers.request.MarketPriceFilterDTO
import dmitriy.losev.cs.dto.offers.request.OffersRequestDTO
import dmitriy.losev.cs.dto.offers.request.ProfitPercentFilterDTO
import dmitriy.losev.cs.dto.offers.request.SalesCountFilterDTO
import dmitriy.losev.cs.dto.offers.response.OffersResponseDTO
import dmitriy.losev.cs.pulse.Market
import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.mapCatchingInData
import dmitriy.losev.cs.usecases.token.GetAuthTokenUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetOffersWithAuthTokenUseCase(
    @Provided private val context: Context,
    private val getOffersUseCase: GetOffersUseCase,
    private val getAuthTokenUseCase: GetAuthTokenUseCase
) : BaseUseCase {

    suspend operator fun invoke(marketConfig: MarketConfig): Result<OffersResponseDTO> {
        return getAuthTokenUseCase.invoke().mapCatchingInData { authToken ->
            getOffersUseCase.invoke(offersRequest = getOffersRequest(authToken, marketConfig))
        }
    }

    private fun getOffersRequest(authToken: String, marketConfig: MarketConfig): OffersRequestDTO {
        return OffersRequestDTO(
            authToken = authToken,
            templateId = context.pulseConfig.templateId,
            firstMarketOptions = getBuyMarketOptions(
                market = marketConfig.buyMarket,
                priceType = marketConfig.buyMarketPriceType,
                minPrice = marketConfig.minPrice,
                maxPrice = marketConfig.maxPrice
            ),
            secondMarketOptions = getSellMarketOptions(market = marketConfig.sellMarket, priceType = marketConfig.sellMarketPriceType),
            profitPercentFilter = getProfitPercentFilter(minProfit = marketConfig.minProfit),
            salesCountFilters = getSalesCountFilters(market = marketConfig.sellMarket)
        )
    }

    private fun getProfitPercentFilter(minProfit: Int): ProfitPercentFilterDTO {
        return ProfitPercentFilterDTO(minValue = minProfit)
    }

    private fun getSalesCountFilters(market: Market): List<SalesCountFilterDTO> {
        return buildList {
            add(
                SalesCountFilterDTO(
                    id = market.filterSalesId,
                    market = market,
                    period = "Week",
                    salesCount = market.minSales
                )
            )
        }
    }

    private fun getBuyMarketOptions(
        market: Market,
        priceType: PriceType,
        minPrice: Double,
        maxPrice: Double
    ): MarketOptionsDTO {
        return MarketOptionsDTO(
            market = market,
            marketPriceFilter = MarketPriceFilterDTO(minValue = minPrice, maxValue = maxPrice),
            marketPriceType = priceType
        )
    }

    private fun getSellMarketOptions(market: Market, priceType: PriceType): MarketOptionsDTO {
        return MarketOptionsDTO(
            market = market,
            marketPriceType = priceType
        )
    }
}
