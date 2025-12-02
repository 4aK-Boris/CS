package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dto.proxy.ProxyConfigDTO
import dmitriy.losev.cs.models.UpsertProxyConfigRequestModel
import org.koin.core.annotation.Factory


@Factory
class UpsertProxyConfigRequestMapper {

    fun map(value: UpsertProxyConfigRequestModel): ProxyConfigDTO {
        return ProxyConfigDTO(
            host = value.host,
            port = value.port,
            login = value.login,
            password = value.password
        )
    }
}
