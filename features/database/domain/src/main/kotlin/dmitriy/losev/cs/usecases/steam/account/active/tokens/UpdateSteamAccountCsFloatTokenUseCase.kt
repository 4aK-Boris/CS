package dmitriy.losev.cs.usecases.steam.account.active.tokens

import dmitriy.losev.cs.repositories.steam.DatabaseActiveSteamAccountRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class UpdateSteamAccountCsFloatTokenUseCase(@Provided private val databaseActiveSteamAccountRepository: DatabaseActiveSteamAccountRepository) : BaseUseCase {

    suspend operator fun invoke(steamId: Long): Result<Unit> = runCatching {
        databaseActiveSteamAccountRepository.updateCsFloatToken(steamId)
    }
}
