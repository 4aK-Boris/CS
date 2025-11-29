package dmitriy.losev.cs.usecases.steam

import dmitriy.losev.cs.dto.steam.SteamAccountDTO
import dmitriy.losev.cs.exceptions.DatabaseException
import dmitriy.losev.cs.repositories.steam.SteamDatabaseAccountRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetSteamAccountBySteamIdUseCase(@Provided private val steamDatabaseAccountRepository: SteamDatabaseAccountRepository) : BaseUseCase {

    suspend operator fun invoke(steamId: ULong): Result<SteamAccountDTO> = runCatching {
        steamDatabaseAccountRepository.getSteamAccountById(steamId) ?: throw DatabaseException.SteamAccountNotFoundException(steamId)
    }
}