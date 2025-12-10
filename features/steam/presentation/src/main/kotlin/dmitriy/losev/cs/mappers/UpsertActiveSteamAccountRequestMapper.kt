package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dto.UpsertActiveSteamAccountRequestDTO
import dmitriy.losev.cs.models.UpsertActiveSteamAccountRequestModel
import org.koin.core.annotation.Factory

@Factory
class UpsertActiveSteamAccountRequestMapper {

    fun map(value: UpsertActiveSteamAccountRequestModel): UpsertActiveSteamAccountRequestDTO {
        return UpsertActiveSteamAccountRequestDTO(
            steamId = value.steamId.toLong(),
            marketCSGOApiToken = value.marketCSGOApiToken
        )
    }
}
