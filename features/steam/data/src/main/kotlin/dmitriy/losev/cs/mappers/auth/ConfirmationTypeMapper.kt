package dmitriy.losev.cs.mappers.auth

import dmitriy.losev.cs.dso.auth.ConfirmationTypeDSO
import dmitriy.losev.cs.dto.auth.ConfirmationTypeDTO
import org.koin.core.annotation.Factory

@Factory
class ConfirmationTypeMapper {

    fun map(value: ConfirmationTypeDSO): ConfirmationTypeDTO {
        return ConfirmationTypeDTO(confirmationType = value.confirmationType)
    }
}
