package dmitriy.losev.cs.usecases.proxy

import dmitriy.losev.cs.repositories.proxy.ProxyDatabaseRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class HasSteamAccountForProxyUseCase(@Provided private val proxyDatabaseRepository: ProxyDatabaseRepository): BaseUseCase {

    suspend operator fun invoke(host: String, port: Int): Result<Boolean> = runCatching {
        proxyDatabaseRepository.hasSteamAccountForProxy(host, port)
    }
}
