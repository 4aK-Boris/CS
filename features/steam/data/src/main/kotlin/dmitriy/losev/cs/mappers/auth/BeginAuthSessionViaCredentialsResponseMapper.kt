package dmitriy.losev.cs.mappers.auth

import dmitriy.losev.cs.dso.auth.BeginAuthSessionViaCredentialsResponseDSO
import dmitriy.losev.cs.dto.auth.BeginAuthSessionViaCredentialsResponseDTO
import org.koin.core.annotation.Factory

@Factory
class BeginAuthSessionViaCredentialsResponseMapper(private val confirmationTypeMapper: ConfirmationTypeMapper) {

    fun map(value: BeginAuthSessionViaCredentialsResponseDSO): BeginAuthSessionViaCredentialsResponseDTO {
        return BeginAuthSessionViaCredentialsResponseDTO(
            clientId = value.clientId,
            requestId = value.requestId,
            interval = value.interval,
            allowedConfirmations = value.allowedConfirmations.map(transform = confirmationTypeMapper::map),
            steamId = value.steamId.toLong(),
            weakToken = value.weakToken,
            extendedErrorMessage = value.extendedErrorMessage
        )
    }
}
