package dmitriy.losev.cs.handlers

import dmitriy.losev.cs.cookie.NetworkCookie

interface CookieHandler {

    suspend fun saveCookies(steamId: ULong, cookies: List<NetworkCookie>)

    suspend fun getCookies(steamId: ULong): List<NetworkCookie>

    suspend fun deleteCookies(steamId: ULong)
}
