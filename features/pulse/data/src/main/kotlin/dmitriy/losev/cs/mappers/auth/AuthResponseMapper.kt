package dmitriy.losev.cs.mappers.auth

import dmitriy.losev.cs.dso.auth.AuthResponseDSO
import dmitriy.losev.cs.dto.auth.AuthResponseDTO
import org.koin.core.annotation.Factory

@Factory
class AuthResponseMapper(private val authDataMapper: AuthDataMapper) {

    fun map(value: AuthResponseDSO): AuthResponseDTO {
        return AuthResponseDTO(
            authData = authDataMapper.map(value = value.authData),
            twoFactorData = value.twoFactorData,
            isEmailConfirmed = value.isEmailConfirmed
        )
    }
}
