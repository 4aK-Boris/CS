package dmitriy.losev.cs.mappers.offers.response

import dmitriy.losev.cs.dso.offers.response.MarketDSO
import dmitriy.losev.cs.dto.offers.response.MarketDTO
import dmitriy.losev.cs.mappers.DateTimeMapper
import org.koin.core.annotation.Factory

@Factory
class MarketMapper(
    private val dateTimeMapper: DateTimeMapper,
    private val holdInfoResponseMapper: HoldInfoResponseMapper
) {

    fun map(value: MarketDSO): MarketDTO {
        return MarketDTO(
            id = value.id,
            price = value.price,
            realPrice = value.realPrice,
            overriddenPrice = value.overriddenPrice,
            realPriceCurrency = value.realPriceCurrency,
            bestOfferCount = value.bestOfferCount ?: 0,
            totalOffersCount = value.totalOffersCount,
            historyUpdateTime = dateTimeMapper.map(value = value.historyUpdateTime),
            offersUpdateTime = dateTimeMapper.map(value.offersUpdateTime),
            overstockInfo = value.overstockInfo,
            holdInfoResponse = value.holdInfoResponse?.let(block = holdInfoResponseMapper::map),
            soldOutTime = value.soldOutTime
        )
    }
}