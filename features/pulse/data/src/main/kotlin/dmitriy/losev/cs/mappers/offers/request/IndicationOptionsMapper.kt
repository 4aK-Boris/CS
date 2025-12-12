package dmitriy.losev.cs.mappers.offers.request

import dmitriy.losev.cs.dso.offers.request.IndicationOptionsDSO
import dmitriy.losev.cs.dto.offers.request.IndicationOptionsDTO
import org.koin.core.annotation.Factory

@Factory
class IndicationOptionsMapper {

    fun map(value: IndicationOptionsDTO): IndicationOptionsDSO {
        return IndicationOptionsDSO(
            isEnabled = value.isEnabled,
            colorIndicators = value.colorIndicators
        )
    }
}
