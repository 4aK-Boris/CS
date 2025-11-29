package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dso.skins.SkinsAvailabilityResponseDSO
import dmitriy.losev.cs.dto.skins.SkinsAvailabilityResponseDTO
import org.koin.core.annotation.Factory

@Factory
class SkinsAvailabilityResponseMapper {

    fun map(value: SkinsAvailabilityResponseDSO): SkinsAvailabilityResponseDTO {
        return SkinsAvailabilityResponseDTO(
            availableSkins = value.skinsAvailabilityData.availableSkins,
            unavailableSkinIds = value.skinsAvailabilityData.unavailableSkinIds
        )
    }
}