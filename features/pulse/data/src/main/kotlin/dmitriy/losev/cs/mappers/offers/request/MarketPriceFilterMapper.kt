package dmitriy.losev.cs.mappers.offers.request

import dmitriy.losev.cs.dso.offers.request.MarketPriceFilterDSO
import dmitriy.losev.cs.dto.offers.request.MarketPriceFilterDTO
import org.koin.core.annotation.Factory

@Factory
class MarketPriceFilterMapper {

    fun map(value: MarketPriceFilterDTO): MarketPriceFilterDSO {
        return MarketPriceFilterDSO(
            minValue = value.minValue,
            maxValue = value.maxValue
        )
    }
}
