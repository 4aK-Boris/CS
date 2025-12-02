package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dto.proxy.ProxyConfigDTO
import dmitriy.losev.cs.models.GetProxyConfigResponseModel
import org.koin.core.annotation.Factory

@Factory
class GetProxyConfigResponseMapper {

    fun map(value: ProxyConfigDTO): GetProxyConfigResponseModel {
        return GetProxyConfigResponseModel(
            host = value.host,
            port = value.port,
            login = value.login,
            password = value.password
        )
    }
}
