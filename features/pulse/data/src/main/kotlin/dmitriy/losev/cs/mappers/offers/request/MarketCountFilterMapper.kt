package dmitriy.losev.cs.mappers.offers.request

import dmitriy.losev.cs.dso.offers.request.MarketCountFilterDSO
import dmitriy.losev.cs.dto.offers.request.MarketCountFilterDTO
import org.koin.core.annotation.Factory

@Factory
class MarketCountFilterMapper {

    fun map(value: MarketCountFilterDTO): MarketCountFilterDSO {
        return MarketCountFilterDSO(
            minValue = value.minValue,
            maxValue = value.maxValue
        )
    }
}
