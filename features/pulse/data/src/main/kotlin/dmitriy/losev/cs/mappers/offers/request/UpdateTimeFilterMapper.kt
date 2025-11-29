package dmitriy.losev.cs.mappers.offers.request

import dmitriy.losev.cs.dso.offers.request.UpdateTimeFilterDSO
import dmitriy.losev.cs.dto.offers.request.UpdateTimeFilterDTO
import org.koin.core.annotation.Factory

@Factory
class UpdateTimeFilterMapper {

    fun map(value: UpdateTimeFilterDTO): UpdateTimeFilterDSO {
        return UpdateTimeFilterDSO(
            minTime = value.minTime,
            maxTime = value.maxTime
        )
    }
}