package dmitriy.losev.cs.usecases.auth

import dmitriy.losev.cs.dto.auth.PasswordRSAPublicKeyRequestDTO
import dmitriy.losev.cs.dto.auth.PasswordRSAPublicKeyResponseDTO
import dmitriy.losev.cs.repositories.SteamAuthRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetPasswordRSAPublicKeyUseCase(@Provided private val steamAuthRepository: SteamAuthRepository) : BaseUseCase {

    suspend operator fun invoke(passwordRSAPublicKeyRequest: PasswordRSAPublicKeyRequestDTO): Result<PasswordRSAPublicKeyResponseDTO> = runCatching {
        steamAuthRepository.getPasswordRSAPublicKey(passwordRSAPublicKeyRequest)
    }
}
