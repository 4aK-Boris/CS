package dmitriy.losev.cs.mappers.offers.request

import dmitriy.losev.cs.dso.offers.request.ProfitPercentFilterDSO
import dmitriy.losev.cs.dto.offers.request.ProfitPercentFilterDTO
import org.koin.core.annotation.Factory

@Factory
class ProfitPercentFilterMapper {

    fun map(value: ProfitPercentFilterDTO): ProfitPercentFilterDSO {
        return ProfitPercentFilterDSO(
            minValue = value.minValue,
            maxValue = value.maxValue
        )
    }
}
