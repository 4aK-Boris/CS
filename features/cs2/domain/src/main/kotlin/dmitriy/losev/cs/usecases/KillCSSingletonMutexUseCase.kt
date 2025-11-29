package dmitriy.losev.cs.usecases

import dmitriy.losev.cs.repositories.CSProcessRepository
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class KillCSSingletonMutexUseCase(@Provided private val csProcessRepository: CSProcessRepository): BaseUseCase {

    suspend operator fun invoke(csProcessId: Int): Result<Unit> = runCatching {
        csProcessRepository.killCSSingletonMutex(csProcessId)
    }
}