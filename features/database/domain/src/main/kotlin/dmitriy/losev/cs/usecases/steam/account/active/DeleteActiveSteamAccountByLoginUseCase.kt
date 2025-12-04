package dmitriy.losev.cs.usecases.steam.account.active

import dmitriy.losev.cs.repositories.steam.DatabaseActiveSteamAccountRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class DeleteActiveSteamAccountByLoginUseCase(@Provided private val databaseActiveSteamAccountRepository: DatabaseActiveSteamAccountRepository) : BaseUseCase {

    suspend operator fun invoke(login: String): Result<Long> = runCatching {
        val deletedSteamId = databaseActiveSteamAccountRepository.deleteActiveSteamAccountByLogin(login)
        requireNotNull(deletedSteamId) { "Couldn't delete active account with login $login" }
    }
}
