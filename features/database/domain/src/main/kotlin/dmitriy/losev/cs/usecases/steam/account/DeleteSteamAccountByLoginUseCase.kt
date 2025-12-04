package dmitriy.losev.cs.usecases.steam.account

import dmitriy.losev.cs.repositories.steam.DatabaseSteamAccountRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class DeleteSteamAccountByLoginUseCase(@Provided private val databaseSteamAccountRepository: DatabaseSteamAccountRepository) : BaseUseCase {

    suspend operator fun invoke(login: String): Result<Long> = runCatching {
        val deletedSteamId = databaseSteamAccountRepository.deleteSteamAccountByLogin(login)
        requireNotNull(deletedSteamId) { "Couldn't delete account with login $login" }
    }
}
