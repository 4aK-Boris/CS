package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dto.proxy.SteamAccountProxyConfigDTO
import dmitriy.losev.cs.models.GetSteamAccountProxyConfigResponseModel
import org.koin.core.annotation.Factory

@Factory
class GetSteamAccountProxyConfigResponseMapper {

    fun map(value: SteamAccountProxyConfigDTO): GetSteamAccountProxyConfigResponseModel {
        return GetSteamAccountProxyConfigResponseModel(
            steamId = value.steamId,
            host = value.host,
            port = value.port,
            login = value.login,
            password = value.password
        )
    }
}
