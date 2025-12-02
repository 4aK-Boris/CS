package dmitriy.losev.cs.usecases.proxy

import dmitriy.losev.cs.repositories.proxy.ProxyDatabaseRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided


@Factory
class GetNumberOfAvailableProxiesUseCase(@Provided private val proxyDatabaseRepository: ProxyDatabaseRepository): BaseUseCase {

    suspend operator fun invoke(): Result<Int> = runCatching {
        proxyDatabaseRepository.getNumberOfAvailableProxies()
    }
}
