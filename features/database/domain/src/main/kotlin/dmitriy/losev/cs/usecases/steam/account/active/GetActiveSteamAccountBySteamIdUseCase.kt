package dmitriy.losev.cs.usecases.steam.account.active

import dmitriy.losev.cs.dto.steam.ActiveSteamAccountDTO
import dmitriy.losev.cs.repositories.steam.DatabaseActiveSteamAccountRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetActiveSteamAccountBySteamIdUseCase(@Provided private val databaseActiveSteamAccountRepository: DatabaseActiveSteamAccountRepository) : BaseUseCase {

    suspend operator fun invoke(steamId: Long): Result<ActiveSteamAccountDTO> = runCatching {
        databaseActiveSteamAccountRepository.getActiveSteamAccountBySteamId(steamId).requireNotNull {
            "Couldn't find active steam account with SteamId $steamId"
        }
    }
}
