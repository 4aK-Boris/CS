package dmitriy.losev.cs.mappers.auth

import dmitriy.losev.cs.dso.auth.PasswordRSAPublicKeyRequestDSO
import dmitriy.losev.cs.dto.auth.PasswordRSAPublicKeyRequestDTO
import org.koin.core.annotation.Factory

@Factory
class PasswordRSAPublicKeyRequestMapper {

    fun map(value: PasswordRSAPublicKeyRequestDTO): PasswordRSAPublicKeyRequestDSO {
        return PasswordRSAPublicKeyRequestDSO(
            steamId = value.steamId,
            login = value.login
        )
    }
}
