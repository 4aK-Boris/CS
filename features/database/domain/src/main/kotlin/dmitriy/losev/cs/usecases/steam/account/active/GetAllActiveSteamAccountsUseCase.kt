package dmitriy.losev.cs.usecases.steam.account.active

import dmitriy.losev.cs.dto.steam.ActiveSteamAccountDTO
import dmitriy.losev.cs.repositories.steam.DatabaseActiveSteamAccountRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetAllActiveSteamAccountsUseCase(@Provided private val databaseActiveSteamAccountRepository: DatabaseActiveSteamAccountRepository) : BaseUseCase {

    suspend operator fun invoke(): Result<List<ActiveSteamAccountDTO>> = runCatching {
        databaseActiveSteamAccountRepository.getAllActiveSteamAccounts()
    }
}
