package dmitriy.losev.cs.usecases.token

import dmitriy.losev.cs.dto.auth.AuthRequestDTO
import dmitriy.losev.cs.dto.auth.AuthResponseDTO
import dmitriy.losev.cs.repositories.PulseRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetAuthTokenFromNetworkUseCase(@Provided private val pulseRepository: PulseRepository): BaseUseCase {

    suspend operator fun invoke(authRequest: AuthRequestDTO): Result<AuthResponseDTO> = runCatching {
        pulseRepository.getAuthTokenFromNetwork(authRequest)
    }
}
