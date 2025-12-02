package dmitriy.losev.cs.usecases.steam

import dmitriy.losev.cs.dto.steam.ActiveSteamAccountDTO
import dmitriy.losev.cs.repositories.steam.SteamDatabaseAccountRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetActiveSteamAccountByLoginUseCase(@Provided private val steamDatabaseAccountRepository: SteamDatabaseAccountRepository) : BaseUseCase {

    suspend operator fun invoke(login: String): Result<ActiveSteamAccountDTO> = runCatching {
        steamDatabaseAccountRepository.getActiveSteamAccountByLogin(login).requireNotNull {
            "Couldn't find active steam account with Login $login"
        }
    }
}
