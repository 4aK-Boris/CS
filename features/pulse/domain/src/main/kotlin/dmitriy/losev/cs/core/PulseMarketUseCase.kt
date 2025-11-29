package dmitriy.losev.cs.core

import dmitriy.losev.cs.dto.offers.response.OfferItemDTO
import dmitriy.losev.cs.dto.pulse.MarketItemDTO
import dmitriy.losev.cs.pulse.Market
import dmitriy.losev.cs.usecases.mapCatchingInData
import dmitriy.losev.cs.usecases.offers.GetOffersWithAuthTokenUseCase
import dmitriy.losev.cs.usecases.pulse.InsertMarketItemsUseCase
import java.time.LocalDateTime

abstract class PulseMarketUseCase(
    private val getOffersWithAuthTokenUseCase: GetOffersWithAuthTokenUseCase,
    private val insertMarketItemsUseCase: InsertMarketItemsUseCase
) {

    abstract val buyMarket: Market

    abstract val sellMarket: Market

    abstract val minProfit: Int

    suspend operator fun invoke(): Result<Unit> {
        return getOffersWithAuthTokenUseCase.invoke(minProfit = minProfit, buyMarket = buyMarket, sellMarket = sellMarket).mapCatchingInData { offers ->
            insertMarketItemsUseCase.invoke(markerItems = offers.map(transform = ::convertToMarketItem))
        }
    }

    private fun convertToMarketItem(offer: OfferItemDTO): MarketItemDTO {
        return MarketItemDTO(
            itemName = offer.itemName.marketHashName,
            minBuyPrice = offer.firstMarket.price,
            maxSellPrice = offer.secondMarket.price,
            buyMarket = buyMarket,
            sellMarket = sellMarket,
            offersCount = offer.firstMarket.bestOfferCount,
            profit = offer.profitPercent,
            lastUpdatedInBuyMarket = offer.firstMarket.offersUpdateTime,
            lastUpdatedInSellMarket = offer.secondMarket.offersUpdateTime,
            firstAddition = currentDateTime
        )
    }

    private val currentDateTime: LocalDateTime
        get() = LocalDateTime.now()
}