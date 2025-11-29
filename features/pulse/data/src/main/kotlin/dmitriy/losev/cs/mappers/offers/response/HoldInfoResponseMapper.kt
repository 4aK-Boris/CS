package dmitriy.losev.cs.mappers.offers.response

import dmitriy.losev.cs.dso.offers.response.HoldInfoResponseDSO
import dmitriy.losev.cs.dto.offers.response.HoldInfoResponseDTO
import org.koin.core.annotation.Factory

@Factory
class HoldInfoResponseMapper {

    fun map(value: HoldInfoResponseDSO): HoldInfoResponseDTO {
        return HoldInfoResponseDTO(
            minHold = value.minHold,
            maxHold = value.maxHold
        )
    }
}