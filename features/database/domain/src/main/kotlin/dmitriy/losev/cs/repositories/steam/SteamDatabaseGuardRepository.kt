package dmitriy.losev.cs.repositories.steam

import dmitriy.losev.cs.dto.steam.SteamAccountCookiesDTO

interface SteamDatabaseGuardRepository {

    suspend fun insertSteamAccountCookies(steamAccountCookie: SteamAccountCookiesDTO): SteamAccountCookiesDTO?

    suspend fun getSteamAccountCookies(steamId: ULong): SteamAccountCookiesDTO?
}