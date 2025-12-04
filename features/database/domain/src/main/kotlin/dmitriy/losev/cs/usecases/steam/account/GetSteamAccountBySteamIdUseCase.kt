package dmitriy.losev.cs.usecases.steam.account

import dmitriy.losev.cs.dto.steam.SteamAccountDTO
import dmitriy.losev.cs.repositories.steam.DatabaseSteamAccountRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetSteamAccountBySteamIdUseCase(@Provided private val databaseSteamAccountRepository: DatabaseSteamAccountRepository) : BaseUseCase {

    suspend operator fun invoke(steamId: Long): Result<SteamAccountDTO> = runCatching {
        databaseSteamAccountRepository.getSteamAccountBySteamId(steamId).requireNotNull {
            "Couldn't find steam account with SteamId $steamId"
        }
    }
}
