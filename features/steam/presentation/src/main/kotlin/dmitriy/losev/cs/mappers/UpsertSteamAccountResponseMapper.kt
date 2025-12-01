package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dto.steam.UpsertSteamAccountResponseDTO
import dmitriy.losev.cs.models.UpsertSteamAccountResponseModel
import org.koin.core.annotation.Factory
import kotlin.time.ExperimentalTime
import kotlin.time.toKotlinInstant

@Factory
@OptIn(ExperimentalTime::class)
class UpsertSteamAccountResponseMapper {

    fun map(value: UpsertSteamAccountResponseDTO): UpsertSteamAccountResponseModel {
        return UpsertSteamAccountResponseModel(
            steamId = value.steamId.toString(),
            login = value.login,
            createdAt = value.createdAt.toKotlinInstant()
        )
    }
}
