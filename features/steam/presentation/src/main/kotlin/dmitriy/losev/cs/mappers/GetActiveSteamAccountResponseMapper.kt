package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dto.steam.ActiveSteamAccountDTO
import dmitriy.losev.cs.models.GetActiveSteamAccountResponseModel
import org.koin.core.annotation.Factory
import kotlin.time.ExperimentalTime
import kotlin.time.toKotlinInstant

@Factory
@OptIn(ExperimentalTime::class)
class GetActiveSteamAccountResponseMapper {

    fun map(value: ActiveSteamAccountDTO): GetActiveSteamAccountResponseModel {
        return GetActiveSteamAccountResponseModel(
            steamId = value.steamId.toString(),
            marketCSGOApiToken = value.marketCSGOApiToken,
            accessToken = value.accessToken,
            refreshToken = value.refreshToken,
            sessionId = value.sessionId,
            createdAt = value.createdAt.toKotlinInstant()
        )
    }
}
