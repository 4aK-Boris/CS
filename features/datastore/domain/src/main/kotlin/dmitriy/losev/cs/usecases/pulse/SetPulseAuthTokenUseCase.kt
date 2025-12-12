package dmitriy.losev.cs.usecases.pulse

import dmitriy.losev.cs.repositories.DataStorePulseRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class SetPulseAuthTokenUseCase(@Provided private val dataStorePulseRepository: DataStorePulseRepository): BaseUseCase {

    suspend operator fun invoke(authToken: String): Result<String> = runCatching {
        dataStorePulseRepository.setAuthToken(authToken)
    }
}
