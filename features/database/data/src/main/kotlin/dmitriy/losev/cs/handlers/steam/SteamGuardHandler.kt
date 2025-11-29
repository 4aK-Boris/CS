package dmitriy.losev.cs.handlers.steam

interface SteamGuardHandler {

    suspend fun insertSteamAccountCookies(steamAccountCookie: SteamAccountCookiesDSO): SteamAccountCookiesDSO?

    suspend fun getSteamAccountCookies(steamId: ULong): SteamAccountCookiesDSO?
}