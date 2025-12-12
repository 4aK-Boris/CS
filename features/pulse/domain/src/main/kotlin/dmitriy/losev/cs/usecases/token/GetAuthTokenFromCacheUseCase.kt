package dmitriy.losev.cs.usecases.token

import dmitriy.losev.cs.repositories.PulseRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetAuthTokenFromCacheUseCase(@Provided private val pulseRepository: PulseRepository) : BaseUseCase {

    suspend operator fun invoke(): Result<String> = runCatching {
        pulseRepository.getAuthTokenFromCache() ?: error(message = "Couldn't get token from cache")
    }
}
