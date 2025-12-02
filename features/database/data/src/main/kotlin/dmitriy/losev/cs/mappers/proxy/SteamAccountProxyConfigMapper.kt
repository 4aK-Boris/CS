package dmitriy.losev.cs.mappers.proxy

import dmitriy.losev.cs.dto.proxy.SteamAccountProxyConfigDTO
import dmitriy.losev.cs.proxy.ProxyConfig
import dmitriy.losev.cs.proxy.SteamAccountProxyConfig
import org.koin.core.annotation.Factory

@Factory
class SteamAccountProxyConfigMapper {

    fun map(value: SteamAccountProxyConfig): SteamAccountProxyConfigDTO {
        return SteamAccountProxyConfigDTO(
            steamId = value.steamId,
            host = value.host,
            port = value.port,
            login = value.login,
            password = value.password
        )
    }

    fun map(value: SteamAccountProxyConfigDTO): SteamAccountProxyConfig {
        return SteamAccountProxyConfig(
            steamId = value.steamId,
            proxyConfig = ProxyConfig.Default(
                host = value.host,
                port = value.port,
                login = value.login,
                password = value.password
            )
        )
    }
}
