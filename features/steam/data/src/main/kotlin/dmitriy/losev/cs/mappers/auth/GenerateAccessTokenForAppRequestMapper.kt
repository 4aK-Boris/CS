package dmitriy.losev.cs.mappers.auth

import dmitriy.losev.cs.dso.auth.GenerateAccessTokenForAppRequestDSO
import dmitriy.losev.cs.dto.auth.GenerateAccessTokenForAppRequestDTO
import org.koin.core.annotation.Factory

@Factory
class GenerateAccessTokenForAppRequestMapper {

    fun map(value: GenerateAccessTokenForAppRequestDTO): GenerateAccessTokenForAppRequestDSO {
        return GenerateAccessTokenForAppRequestDSO(
            steamId = value.steamId,
            refreshToken = value.refreshToken
        )
    }
}
