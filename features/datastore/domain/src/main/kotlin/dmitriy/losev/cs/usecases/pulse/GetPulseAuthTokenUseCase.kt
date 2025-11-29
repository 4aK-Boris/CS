package dmitriy.losev.cs.usecases.pulse

import dmitriy.losev.cs.exceptions.DataStoreException
import dmitriy.losev.cs.repositories.DataStorePulseRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetPulseAuthTokenUseCase(@Provided private val dataStorePulseRepository: DataStorePulseRepository): BaseUseCase {

    suspend operator fun invoke(): Result<String> = runCatching {
        dataStorePulseRepository.getAuthToken().ifBlank { throw DataStoreException.NullablePulseAuthTokenException() }
    }
}