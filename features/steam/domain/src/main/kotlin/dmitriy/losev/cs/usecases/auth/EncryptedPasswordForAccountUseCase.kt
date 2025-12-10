package dmitriy.losev.cs.usecases.auth

import dmitriy.losev.cs.dto.auth.PasswordRSAPublicKeyRequestDTO
import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.guard.EncryptPasswordUseCase
import org.koin.core.annotation.Factory

@Factory
class EncryptedPasswordForAccountUseCase(
    private val getPasswordRSAPublicKeyUseCase: GetPasswordRSAPublicKeyUseCase,
    private val encryptPasswordUseCase: EncryptPasswordUseCase
) : BaseUseCase {

    suspend operator fun invoke(steamId: Long, login: String, password: String): Result<Pair<String, Long>> {
        val passwordRSAPublicKeyRequest = PasswordRSAPublicKeyRequestDTO(steamId, login)
        return getPasswordRSAPublicKeyUseCase.invoke(passwordRSAPublicKeyRequest).mapCatching { passwordRSAPublicKeyResponse ->
            encryptPasswordUseCase.invoke(
                password = password,
                modulus = passwordRSAPublicKeyResponse.publickeyModulus,
                exponent = passwordRSAPublicKeyResponse.publickeyExponent
            ).getOrThrow() to passwordRSAPublicKeyResponse.timeStamp
        }
    }
}
