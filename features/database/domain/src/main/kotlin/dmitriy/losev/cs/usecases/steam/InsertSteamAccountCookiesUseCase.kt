package dmitriy.losev.cs.usecases.steam

import dmitriy.losev.cs.dto.steam.SteamAccountCookiesDTO
import dmitriy.losev.cs.exceptions.SteamException
import dmitriy.losev.cs.repositories.steam.SteamDatabaseGuardRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class InsertSteamAccountCookiesUseCase(@Provided private val steamDatabaseGuardRepository: SteamDatabaseGuardRepository) : BaseUseCase {

    suspend operator fun invoke(steamAccountCookies: SteamAccountCookiesDTO): Result<SteamAccountCookiesDTO> = runCatching {
        steamDatabaseGuardRepository.insertSteamAccountCookies(steamAccountCookies) ?: throw SteamException.NullableSteamAccountCookiesException(steamId = steamAccountCookies.steamId)
    }
}