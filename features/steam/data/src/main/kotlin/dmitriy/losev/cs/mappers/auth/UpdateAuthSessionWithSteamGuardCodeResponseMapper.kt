package dmitriy.losev.cs.mappers.auth

import dmitriy.losev.cs.dso.auth.UpdateAuthSessionWithSteamGuardCodeResponseDSO
import dmitriy.losev.cs.dto.auth.UpdateAuthSessionWithSteamGuardCodeResponseDTO
import org.koin.core.annotation.Factory

@Factory
class UpdateAuthSessionWithSteamGuardCodeResponseMapper {

    fun map(value: UpdateAuthSessionWithSteamGuardCodeResponseDSO): UpdateAuthSessionWithSteamGuardCodeResponseDTO {
        return UpdateAuthSessionWithSteamGuardCodeResponseDTO(agreementSessionUrl = value.agreementSessionUrl)
    }
}
