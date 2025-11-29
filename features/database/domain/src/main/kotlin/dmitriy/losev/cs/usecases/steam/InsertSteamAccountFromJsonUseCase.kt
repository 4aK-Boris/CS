package dmitriy.losev.cs.usecases.steam

import dmitriy.losev.cs.repositories.steam.SteamDatabaseAccountRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class InsertSteamAccountFromJsonUseCase(@Provided private val steamDatabaseAccountRepository: SteamDatabaseAccountRepository) : BaseUseCase {

    suspend operator fun invoke(steamAccountString: String): Result<ULong> = runCatching {
        steamDatabaseAccountRepository.insertSteamAccount(steamAccountString)
    }
}