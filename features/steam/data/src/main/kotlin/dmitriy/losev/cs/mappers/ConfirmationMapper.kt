package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.core.ConfirmationType
import dmitriy.losev.cs.dso.ConfirmationDSO
import dmitriy.losev.cs.dto.ConfirmationDTO
import org.koin.core.annotation.Factory

@Factory
class ConfirmationMapper {

    fun map(value: ConfirmationDSO): ConfirmationDTO {
        return ConfirmationDTO(
            id = value.id,
            key = value.key,
            type = ConfirmationType.entries[value.type],
            creatorId = value.creatorId
        )
    }

    fun map(value: ConfirmationDTO): ConfirmationDSO {
        return ConfirmationDSO(
            id = value.id,
            key = value.key,
            type = value.type.ordinal,
            creatorId = value.creatorId
        )
    }
}
