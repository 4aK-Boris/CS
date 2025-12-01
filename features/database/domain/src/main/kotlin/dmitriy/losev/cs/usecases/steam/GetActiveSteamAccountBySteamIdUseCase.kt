package dmitriy.losev.cs.usecases.steam

import dmitriy.losev.cs.dto.steam.SteamAccountDTO
import dmitriy.losev.cs.repositories.steam.SteamDatabaseAccountRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetActiveSteamAccountBySteamIdUseCase(@Provided private val steamDatabaseAccountRepository: SteamDatabaseAccountRepository) : BaseUseCase {

    suspend operator fun invoke(steamId: Long): Result<SteamAccountDTO> = runCatching {
        steamDatabaseAccountRepository.getSteamAccountBySteamId(steamId).requireNotNull {
            "Couldn't find active steam account with SteamId $steamId"
        }
    }
}
