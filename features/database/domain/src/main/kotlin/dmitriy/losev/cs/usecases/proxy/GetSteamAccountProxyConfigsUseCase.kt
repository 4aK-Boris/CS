package dmitriy.losev.cs.usecases.proxy

import dmitriy.losev.cs.dto.proxy.SteamAccountProxyConfigDTO
import dmitriy.losev.cs.repositories.proxy.ProxyDatabaseRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetSteamAccountProxyConfigsUseCase(@Provided private val proxyDatabaseRepository: ProxyDatabaseRepository): BaseUseCase {

    suspend operator fun invoke(): Result<List<SteamAccountProxyConfigDTO>> = runCatching {
        proxyDatabaseRepository.getSteamAccountProxyConfigs()
    }
}

