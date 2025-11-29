package dmitriy.losev.cs.repositories.steam

import dmitriy.losev.cs.dto.steam.SteamAccountCookiesDTO
import dmitriy.losev.cs.handlers.steam.SteamGuardHandler
import dmitriy.losev.cs.mappers.steam.SteamAccountCookiesMapper
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory(binds = [SteamDatabaseGuardRepository::class])
class SteamDatabaseGuardRepositoryImpl(
    @Provided private val steamGuardHandler: SteamGuardHandler,
    private val steamAccountCookiesMapper: SteamAccountCookiesMapper
): SteamDatabaseGuardRepository {

    override suspend fun insertSteamAccountCookies(steamAccountCookie: SteamAccountCookiesDTO): SteamAccountCookiesDTO? {
        return steamGuardHandler.insertSteamAccountCookies(steamAccountCookie = steamAccountCookiesMapper.map(value = steamAccountCookie))?.run(block = steamAccountCookiesMapper::map)
    }

    override suspend fun getSteamAccountCookies(steamId: ULong): SteamAccountCookiesDTO? {
        return steamGuardHandler.getSteamAccountCookies(steamId)?.run(block = steamAccountCookiesMapper::map)
    }
}