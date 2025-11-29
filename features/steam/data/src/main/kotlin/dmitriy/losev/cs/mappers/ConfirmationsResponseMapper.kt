package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dso.ConfirmationsResponseDSO
import dmitriy.losev.cs.dto.ConfirmationsResponseDTO
import org.koin.core.annotation.Factory

@Factory
class ConfirmationsResponseMapper(private val confirmationMapper: ConfirmationMapper) {

    fun map(value: ConfirmationsResponseDSO): ConfirmationsResponseDTO {
        return ConfirmationsResponseDTO(
            success = value.success,
            confirmations = value.confirmations?.map(transform = confirmationMapper::map)
        )
    }
}