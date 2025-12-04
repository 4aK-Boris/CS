package dmitriy.losev.cs.usecases.steam.account.active

import dmitriy.losev.cs.repositories.steam.DatabaseActiveSteamAccountRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class DeleteActiveSteamAccountBySteamIdUseCase(@Provided private val databaseActiveSteamAccountRepository: DatabaseActiveSteamAccountRepository) : BaseUseCase {

    suspend operator fun invoke(steamId: Long): Result<Long> = runCatching {
        val deletedSteamId = databaseActiveSteamAccountRepository.deleteActiveSteamAccountBySteamId(steamId)
        requireNotNull(deletedSteamId) { "Couldn't delete active account with Steam Id $steamId" }
    }
}
