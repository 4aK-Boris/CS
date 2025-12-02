package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.EMPTY_STRING
import dmitriy.losev.cs.dto.steam.ActiveSteamAccountDTO
import dmitriy.losev.cs.models.UpsertActiveSteamAccountRequestModel
import java.time.Instant
import org.koin.core.annotation.Factory

@Factory
class UpsertActiveSteamAccountRequestMapper {

    fun map(value: UpsertActiveSteamAccountRequestModel): ActiveSteamAccountDTO {
        return ActiveSteamAccountDTO(
            steamId = value.steamId.toLong(),
            marketCSGOApiToken = value.marketCSGOApiToken,
            accessToken = EMPTY_STRING,
            refreshToken = EMPTY_STRING,
            sessionId = EMPTY_STRING,
            createdAt = Instant.now()
        )
    }
}
