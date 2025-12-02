package dmitriy.losev.cs.mappers.steam

import dmitriy.losev.cs.dso.steam.UpsertActiveSteamAccountResponseDSO
import dmitriy.losev.cs.dto.steam.UpsertActiveSteamAccountResponseDTO
import org.koin.core.annotation.Factory

@Factory
class UpsertActiveSteamAccountResponseMapper {

    fun map(value: UpsertActiveSteamAccountResponseDSO): UpsertActiveSteamAccountResponseDTO {
        return UpsertActiveSteamAccountResponseDTO(
            steamId = value.steamId,
            login = value.login,
            createdAt = value.createdAt
        )
    }
}

