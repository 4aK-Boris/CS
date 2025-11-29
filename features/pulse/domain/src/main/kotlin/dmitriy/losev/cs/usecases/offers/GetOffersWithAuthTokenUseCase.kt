package dmitriy.losev.cs.usecases.offers

import dmitriy.losev.cs.Context
import dmitriy.losev.cs.dto.offers.request.MarketOptionsDTO
import dmitriy.losev.cs.dto.offers.request.MarketPriceFilterDTO
import dmitriy.losev.cs.dto.offers.request.OffersRequestDTO
import dmitriy.losev.cs.dto.offers.request.ProfitPercentFilterDTO
import dmitriy.losev.cs.dto.offers.request.SalesCountFilterDTO
import dmitriy.losev.cs.dto.offers.response.OffersResponseDTO
import dmitriy.losev.cs.pulse.Market
import dmitriy.losev.cs.pulse.Period
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

    suspend operator fun invoke(minProfit: Int, buyMarket: Market, sellMarket: Market): Result<OffersResponseDTO> {
        return getAuthTokenUseCase.invoke().mapCatchingInData { authToken ->
            getOffersUseCase.invoke(offersRequest = getOffersRequest(authToken, minProfit, buyMarket, sellMarket))
        }
    }

    private fun getOffersRequest(authToken: String, minProfit: Int, firstMarket: Market, secondMarket: Market): OffersRequestDTO {
        return OffersRequestDTO(
            authToken = authToken,
            templateId = context.pulseConfig.templateId,
            firstMarketOptions = getBuyMarketOptions(market = firstMarket),
            secondMarketOptions = getSellMarketOptions(market = secondMarket),
            profitPercentFilter = getProfitPercentFilter(minProfit = minProfit),
            salesCountFilters = getSalesCountFilters(market = secondMarket)
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
                    period = Period.WEEK,
                    salesCount = market.minSales
                )
            )
        }
    }

    private fun getBuyMarketOptions(market: Market): MarketOptionsDTO {
        return MarketOptionsDTO(
            market = market,
            marketPriceFilter = MarketPriceFilterDTO(minValue = 0.5),
            marketPriceType = market.firstPriceType
        )
    }

    private fun getSellMarketOptions(market: Market): MarketOptionsDTO {
        return MarketOptionsDTO(
            market = market,
            marketPriceType = market.secondPriceType
        )
    }
}