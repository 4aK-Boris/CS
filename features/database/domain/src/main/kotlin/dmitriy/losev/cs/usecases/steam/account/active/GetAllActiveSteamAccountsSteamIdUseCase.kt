package dmitriy.losev.cs.usecases.steam.account.active

import dmitriy.losev.cs.repositories.steam.DatabaseActiveSteamAccountRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetAllActiveSteamAccountsSteamIdUseCase(@Provided private val databaseActiveSteamAccountRepository: DatabaseActiveSteamAccountRepository) : BaseUseCase {

    suspend operator fun invoke(): Result<List<Long>> = runCatching {
        databaseActiveSteamAccountRepository.getAllActiveSteamAccountsSteamId()
    }
}
