package dmitriy.losev.cs.usecases.steam.account

import dmitriy.losev.cs.dto.steam.SteamAccountCredentialsDTO
import dmitriy.losev.cs.repositories.steam.DatabaseSteamAccountRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided


@Factory
class GetSteamAccountCredentialsUseCase(@Provided private val databaseSteamAccountRepository: DatabaseSteamAccountRepository) : BaseUseCase {

    suspend operator fun invoke(steamId: Long): Result<SteamAccountCredentialsDTO> = runCatching {
        databaseSteamAccountRepository.getSteamAccountCredentials(steamId).requireNotNull {
            "Couldn't find steam account credentials with SteamId $steamId"
        }
    }
}
