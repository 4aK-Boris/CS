package dmitriy.losev.cs.mappers.auth

import dmitriy.losev.cs.dso.auth.UpdateAuthSessionWithSteamGuardCodeRequestDSO
import dmitriy.losev.cs.dto.auth.UpdateAuthSessionWithSteamGuardCodeRequestDTO
import org.koin.core.annotation.Factory

@Factory
class UpdateAuthSessionWithSteamGuardCodeRequestMapper {

    fun map(value: UpdateAuthSessionWithSteamGuardCodeRequestDTO): UpdateAuthSessionWithSteamGuardCodeRequestDSO {
        return UpdateAuthSessionWithSteamGuardCodeRequestDSO(
            steamId = value.steamId,
            clientId = value.clientId,
            steamGuardCode = value.steamGuardCode,
            codeType = value.codeType.toString()
        )
    }
}
