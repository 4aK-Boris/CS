package dmitriy.losev.cs.usecases.steam.account

import dmitriy.losev.cs.dto.steam.SteamAccountDTO
import dmitriy.losev.cs.repositories.steam.DatabaseSteamAccountRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetSteamAccountByLoginUseCase(@Provided private val databaseSteamAccountRepository: DatabaseSteamAccountRepository) : BaseUseCase {

    suspend operator fun invoke(login: String): Result<SteamAccountDTO> = runCatching {
        databaseSteamAccountRepository.getSteamAccountByLogin(login).requireNotNull {
            "Couldn't find steam account with Login $login"
        }
    }
}
