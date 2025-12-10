package dmitriy.losev.cs.usecases.auth

import dmitriy.losev.cs.dto.auth.GenerateAccessTokenForAppRequestDTO
import dmitriy.losev.cs.dto.auth.GenerateAccessTokenForAppResponseDTO
import dmitriy.losev.cs.repositories.SteamAuthRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GenerateAccessTokenForAppUseCase(@Provided private val steamAuthRepository: SteamAuthRepository) : BaseUseCase {

    suspend operator fun invoke(generateAccessTokenForAppRequest: GenerateAccessTokenForAppRequestDTO): Result<GenerateAccessTokenForAppResponseDTO> = runCatching {
        steamAuthRepository.generateAccessTokenForApp(generateAccessTokenForAppRequest)
    }
}
