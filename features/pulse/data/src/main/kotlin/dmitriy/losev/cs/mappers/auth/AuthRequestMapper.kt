package dmitriy.losev.cs.mappers.auth

import dmitriy.losev.cs.dso.auth.AuthRequestDSO
import dmitriy.losev.cs.dto.auth.AuthRequestDTO
import org.koin.core.annotation.Factory

@Factory
class AuthRequestMapper {

    fun map(value: AuthRequestDTO): AuthRequestDSO {
        return AuthRequestDSO(
            email = value.email,
            password = value.password
        )
    }
}