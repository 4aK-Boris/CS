package dmitriy.losev.cs.mappers.steam

import dmitriy.losev.cs.dso.steam.UpsertSteamAccountResponseDSO
import dmitriy.losev.cs.dto.steam.UpsertSteamAccountResponseDTO
import org.koin.core.annotation.Factory

@Factory
class UpsertSteamAccountResponseMapper {

    fun map(value: UpsertSteamAccountResponseDSO): UpsertSteamAccountResponseDTO {
        return UpsertSteamAccountResponseDTO(
            steamId = value.steamId,
            login = value.login,
            createdAt = value.createdAt
        )
    }
}
