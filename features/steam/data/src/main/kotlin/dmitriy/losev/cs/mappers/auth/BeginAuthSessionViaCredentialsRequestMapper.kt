package dmitriy.losev.cs.mappers.auth

import dmitriy.losev.cs.dso.auth.BeginAuthSessionViaCredentialsRequestDSO
import dmitriy.losev.cs.dto.auth.BeginAuthSessionViaCredentialsRequestDTO
import org.koin.core.annotation.Factory

@Factory
class BeginAuthSessionViaCredentialsRequestMapper {

    fun map(value: BeginAuthSessionViaCredentialsRequestDTO): BeginAuthSessionViaCredentialsRequestDSO {
        return BeginAuthSessionViaCredentialsRequestDSO(
            steamId = value.steamId,
            login = value.login,
            encryptedPassword = value.encryptedPassword,
            timeStamp = value.timeStamp.toString()
        )
    }
}
