package dmitriy.losev.cs.usecases.auth

import dmitriy.losev.cs.dto.auth.PollAuthSessionStatusRequestDTO
import dmitriy.losev.cs.dto.auth.PollAuthSessionStatusResponseDTO
import dmitriy.losev.cs.repositories.SteamAuthRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class PollAuthSessionStatusUseCase(@Provided private val steamAuthRepository: SteamAuthRepository) : BaseUseCase {

    suspend operator fun invoke(pollAuthSessionStatusRequest: PollAuthSessionStatusRequestDTO): Result<PollAuthSessionStatusResponseDTO> = runCatching {
        steamAuthRepository.pollAuthSessionStatus(pollAuthSessionStatusRequest)
    }
}
