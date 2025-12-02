package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dto.steam.SteamAccountDTO
import dmitriy.losev.cs.models.GetSteamAccountResponseModel
import org.koin.core.annotation.Factory
import kotlin.time.ExperimentalTime
import kotlin.time.toKotlinInstant

@Factory
@OptIn(ExperimentalTime::class)
class GetSteamAccountResponseMapper {

    fun map(value: SteamAccountDTO): GetSteamAccountResponseModel {
        return GetSteamAccountResponseModel(
            steamId = value.steamId.toString(),
            login = value.login,
            password = value.password,
            sharedSecret = value.sharedSecret,
            identitySecret = value.identitySecret,
            revocationCode = value.revocationCode,
            deviceId = value.deviceId,
            createdAt = value.createdAt.toKotlinInstant()
        )
    }
}
