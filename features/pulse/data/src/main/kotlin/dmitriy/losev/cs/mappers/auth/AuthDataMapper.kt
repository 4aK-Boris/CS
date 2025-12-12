package dmitriy.losev.cs.mappers.auth

import dmitriy.losev.cs.dso.auth.AuthDataDSO
import dmitriy.losev.cs.dto.auth.AuthDataDTO
import org.koin.core.annotation.Factory

@Factory
class AuthDataMapper {

    fun map(value: AuthDataDSO): AuthDataDTO {
        return AuthDataDTO(
            token = value.token,
            email = value.email
        )
    }
}
