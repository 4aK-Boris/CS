package dmitriy.losev.cs.usecases.auth

import dmitriy.losev.cs.dto.auth.BeginAuthSessionViaCredentialsRequestDTO
import dmitriy.losev.cs.dto.auth.BeginAuthSessionViaCredentialsResponseDTO
import dmitriy.losev.cs.repositories.SteamAuthRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class BeginAuthSessionViaCredentialsUseCase(@Provided private val steamAuthRepository: SteamAuthRepository) : BaseUseCase {

    suspend operator fun invoke(beginAuthSessionViaCredentialsRequest: BeginAuthSessionViaCredentialsRequestDTO): Result<BeginAuthSessionViaCredentialsResponseDTO> = runCatching {
        steamAuthRepository.beginAuthSessionViaCredentials(beginAuthSessionViaCredentialsRequest)
    }
}
