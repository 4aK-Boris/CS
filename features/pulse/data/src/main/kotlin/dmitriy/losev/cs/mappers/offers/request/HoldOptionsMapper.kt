package dmitriy.losev.cs.mappers.offers.request

import dmitriy.losev.cs.dso.offers.request.HoldOptionsDSO
import dmitriy.losev.cs.dto.offers.request.HoldOptionsDTO
import org.koin.core.annotation.Factory

@Factory
class HoldOptionsMapper {

    fun map(value: HoldOptionsDTO): HoldOptionsDSO {
        return HoldOptionsDSO(
            minHold = value.minHold,
            maxHold = value.maxHold
        )
    }
}