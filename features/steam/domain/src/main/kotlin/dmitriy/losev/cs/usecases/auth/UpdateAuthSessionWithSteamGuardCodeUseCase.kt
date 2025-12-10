package dmitriy.losev.cs.usecases.auth

import dmitriy.losev.cs.dto.auth.UpdateAuthSessionWithSteamGuardCodeRequestDTO
import dmitriy.losev.cs.dto.auth.UpdateAuthSessionWithSteamGuardCodeResponseDTO
import dmitriy.losev.cs.repositories.SteamAuthRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class UpdateAuthSessionWithSteamGuardCodeUseCase(@Provided private val steamAuthRepository: SteamAuthRepository) : BaseUseCase {

    suspend operator fun invoke(
        updateAuthSessionWithSteamGuardCodeRequest: UpdateAuthSessionWithSteamGuardCodeRequestDTO
    ): Result<UpdateAuthSessionWithSteamGuardCodeResponseDTO> = runCatching {
        steamAuthRepository.updateAuthSessionWithSteamGuardCode(updateAuthSessionWithSteamGuardCodeRequest)
    }
}
