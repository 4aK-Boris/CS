package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dto.steam.UpsertActiveSteamAccountResponseDTO
import dmitriy.losev.cs.models.UpsertActiveSteamAccountResponseModel
import org.koin.core.annotation.Factory
import kotlin.time.ExperimentalTime
import kotlin.time.toKotlinInstant

@Factory
@OptIn(ExperimentalTime::class)
class UpsertActiveSteamAccountResponseMapper {

    fun map(value: UpsertActiveSteamAccountResponseDTO): UpsertActiveSteamAccountResponseModel {
        return UpsertActiveSteamAccountResponseModel(
            steamId = value.steamId.toString(),
            login = value.login,
            createdAt = value.createdAt.toKotlinInstant()
        )
    }
}
