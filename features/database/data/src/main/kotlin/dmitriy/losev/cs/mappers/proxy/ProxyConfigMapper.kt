package dmitriy.losev.cs.mappers.proxy

import dmitriy.losev.cs.dto.proxy.ProxyConfigDTO
import dmitriy.losev.cs.proxy.ProxyConfig
import org.koin.core.annotation.Factory

@Factory
class ProxyConfigMapper {

    fun map(value: ProxyConfig): ProxyConfigDTO {
        return ProxyConfigDTO(
            host = value.host,
            port = value.port,
            login = value.login,
            password = value.password
        )
    }

    fun map(value: ProxyConfigDTO): ProxyConfig {
        return ProxyConfig.Default(
            host = value.host,
            port = value.port,
            login = value.login,
            password = value.password
        )
    }
}
