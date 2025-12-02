package dmitriy.losev.cs.usecases.proxy

import dmitriy.losev.cs.dto.proxy.ProxyConfigDTO
import dmitriy.losev.cs.repositories.proxy.ProxyDatabaseRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class UpsertProxyConfigsUseCase(@Provided private val proxyDatabaseRepository: ProxyDatabaseRepository): BaseUseCase {

    suspend operator fun invoke(proxyConfigs: List<ProxyConfigDTO>): Result<Int> = runCatching {
        proxyDatabaseRepository.upsertProxyConfigs(proxyConfigs)
    }
}
