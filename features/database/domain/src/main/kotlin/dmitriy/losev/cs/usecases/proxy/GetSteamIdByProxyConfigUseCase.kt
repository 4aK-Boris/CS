package dmitriy.losev.cs.usecases.proxy

import dmitriy.losev.cs.repositories.proxy.ProxyDatabaseRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetSteamIdByProxyConfigUseCase(@Provided private val proxyDatabaseRepository: ProxyDatabaseRepository) : BaseUseCase {

    suspend operator fun invoke(host: String, port: Int): Result<Long> = runCatching {
        proxyDatabaseRepository.getSteamIdByProxyConfig(host, port).requireNotNull {
            "There is no steam account installed for the proxy"
        }
    }
}
