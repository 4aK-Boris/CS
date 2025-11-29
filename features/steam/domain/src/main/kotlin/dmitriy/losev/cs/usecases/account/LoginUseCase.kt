package dmitriy.losev.cs.usecases.account

import dmitriy.losev.cs.dto.steam.SteamAccountCookiesDTO
import dmitriy.losev.cs.repositories.SteamAccountRepository
import dmitriy.losev.cs.repositories.steam.SteamDatabaseAccountRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class LoginUseCase(@Provided private val steamAccountRepository: SteamAccountRepository) : BaseUseCase {

    suspend operator fun invoke(
        steamId: ULong,
        login: String,
        encryptedPassword: String,
        steamGuardCode: String,
        rsaKeyTimeStamp: Long
    ): Result<SteamAccountCookiesDTO> = runCatching {
        steamAccountRepository.login(steamId, login, encryptedPassword, steamGuardCode, rsaKeyTimeStamp)
    }
}