package dmitriy.losev.cs.usecases.steam

import dmitriy.losev.cs.dto.steam.SteamAccountDTO
import dmitriy.losev.cs.repositories.steam.SteamDatabaseAccountRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetSteamAccountByLoginUseCase(@Provided private val steamDatabaseAccountRepository: SteamDatabaseAccountRepository) : BaseUseCase {

    suspend operator fun invoke(login: String): Result<SteamAccountDTO> = runCatching {
        steamDatabaseAccountRepository.getSteamAccountByLogin(login).requireNotNull {
            "Couldn't find steam account with Login $login"
        }
    }
}
