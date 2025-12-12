package dmitriy.losev.cs.usecases.offers

import dmitriy.losev.cs.core.PriceType
import dmitriy.losev.cs.dto.offers.MarketConfig
import dmitriy.losev.cs.dto.offers.response.OfferItemDTO
import dmitriy.losev.cs.dto.pulse.MarketItemDTO
import dmitriy.losev.cs.pulse.Market
import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.pulse.UpsertMarketItemsUseCase
import org.koin.core.annotation.Factory
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Factory
@OptIn(ExperimentalTime::class)
class GetAndSaveOffersUseCase(
    private val getMarketStateUseCase: GetMarketStateUseCase,
    private val getOffersWithAuthTokenUseCase: GetOffersWithAuthTokenUseCase,
    private val upsertMarketItemsUseCase: UpsertMarketItemsUseCase,
    private val setMarketPriceUpdateAtUseCase: SetMarketPriceUpdateAtUseCase,
    private val getMarketPriceUpdateAtUseCase: GetMarketPriceUpdateAtUseCase
) : BaseUseCase {

    suspend operator fun invoke(marketConfig: MarketConfig): Result<Unit> {
        return getMarketStateUseCase.invoke(marketConfig.buyMarket, marketConfig.sellMarket).mapCatching { marketState ->
            val marketPriceUpdateAt = getMarketPriceUpdateAtUseCase.invoke(marketConfig.buyMarket, marketConfig.sellMarket).getOrThrow()
            if (marketState || (currentTime > marketPriceUpdateAt.plus(duration = 1.hours))) {
                val offersResponse = getOffersWithAuthTokenUseCase.invoke(marketConfig).getOrThrow()
                val buyMarketItems = offersResponse.map { offerItem ->
                    convertToBuyMarketItem(offerItem = offerItem, market = marketConfig.buyMarket, priceType = marketConfig.buyMarketPriceType)
                }
                val sellMarketItems = offersResponse.map { offerItem ->
                    convertToSellMarketItem(offerItem = offerItem, market = marketConfig.sellMarket, priceType = marketConfig.sellMarketPriceType)
                }
                upsertMarketItemsUseCase.invoke(markerItems = buyMarketItems + sellMarketItems).getOrThrow()
                setMarketPriceUpdateAtUseCase.invoke(marketConfig.buyMarket, marketConfig.sellMarket).getOrThrow()
            }
        }
    }

    private fun convertToBuyMarketItem(offerItem: OfferItemDTO, market: Market, priceType: PriceType): MarketItemDTO {
        val price = offerItem.firstMarket.price.times(100).toInt()
        return MarketItemDTO(
            marketHashName = offerItem.itemName.marketHashName,
            market = market,
            minPrice = if (priceType == PriceType.SELL) price else null,
            buyOrderPrice = if (priceType == PriceType.BUY) price else null,
            tradeOnPrice = if (priceType == PriceType.TRADE_ON) price else null,
            weeklySalesCount = offerItem.itemPopularity.find { (m, _, _) -> m == market }?.salesCount ?: 0,
            createdAt = currentTime
        )
    }

    private fun convertToSellMarketItem(offerItem: OfferItemDTO, market: Market, priceType: PriceType): MarketItemDTO {
        val price = offerItem.secondMarket.price.times(100).toInt()
        return MarketItemDTO(
            marketHashName = offerItem.itemName.marketHashName,
            market = market,
            minPrice = if (priceType == PriceType.SELL) price else null,
            buyOrderPrice = if (priceType == PriceType.BUY) price else null,
            tradeOnPrice = if (priceType == PriceType.TRADE_ON) price else null,
            weeklySalesCount = offerItem.itemPopularity.find { (m, _, _) -> m == market }?.salesCount ?: 0,
            createdAt = currentTime
        )
    }

    private val currentTime: Instant
        get() = Clock.System.now()
}
