package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dto.steam.SteamAccountDTO
import dmitriy.losev.cs.models.UpsertSteamAccountRequestModel
import java.time.Instant
import org.koin.core.annotation.Factory

@Factory
class UpsertSteamAccountRequestMapper {

    fun map(value: UpsertSteamAccountRequestModel): SteamAccountDTO {
        return SteamAccountDTO(
            steamId = value.steamId.toLong(),
            login = value.login,
            password = value.password,
            sharedSecret = value.sharedSecret,
            identitySecret = value.identitySecret,
            revocationCode = value.revocationCode,
            deviceId = value.deviceId,
            createdAt = Instant.now()
        )
    }
}
