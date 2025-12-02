package dmitriy.losev.cs.usecases.steam

import dmitriy.losev.cs.dto.steam.ActiveSteamAccountDTO
import dmitriy.losev.cs.repositories.steam.SteamDatabaseAccountRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetAllActiveSteamAccountsUseCase(@Provided private val steamDatabaseAccountRepository: SteamDatabaseAccountRepository) : BaseUseCase {

    suspend operator fun invoke(): Result<List<ActiveSteamAccountDTO>> = runCatching {
        steamDatabaseAccountRepository.getAllActiveSteamAccounts()
    }
}
