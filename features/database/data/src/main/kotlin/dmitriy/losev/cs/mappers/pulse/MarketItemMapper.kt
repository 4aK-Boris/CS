package dmitriy.losev.cs.mappers.pulse

import dmitriy.losev.cs.dso.pulse.MarketItemDSO
import dmitriy.losev.cs.dto.pulse.MarketItemDTO
import dmitriy.losev.cs.pulse.Market
import org.koin.core.annotation.Factory

@Factory
class MarketItemMapper {

    fun map(value: MarketItemDSO): MarketItemDTO {
        return MarketItemDTO(
            itemName = value.itemName,
            minBuyPrice = value.minBuyPrice,
            maxSellPrice = value.maxSellPrice,
            buyMarket = Market.valueOf(value.buyMarket),
            sellMarket = Market.valueOf(value.sellMarket),
            offersCount = value.offersCount,
            profit = value.profit,
            lastUpdatedInBuyMarket = value.lastUpdatedInBuyMarket,
            lastUpdatedInSellMarket = value.lastUpdatedInSellMarket,
            firstAddition = value.firstAddition
        )
    }

    fun map(value: MarketItemDTO): MarketItemDSO {
        return MarketItemDSO(
            itemName = value.itemName,
            minBuyPrice = value.minBuyPrice,
            maxSellPrice = value.maxSellPrice,
            buyMarket = value.buyMarket.name,
            sellMarket = value.sellMarket.name,
            offersCount = value.offersCount,
            profit = value.profit,
            lastUpdatedInBuyMarket = value.lastUpdatedInBuyMarket,
            lastUpdatedInSellMarket = value.lastUpdatedInSellMarket,
            firstAddition = value.firstAddition
        )
    }
}