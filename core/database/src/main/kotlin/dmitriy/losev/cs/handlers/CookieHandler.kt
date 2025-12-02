package dmitriy.losev.cs.handlers

import dmitriy.losev.cs.cookie.NetworkCookie

interface CookieHandler {

    suspend fun saveCookies(steamId: Long, cookies: List<NetworkCookie>)

    suspend fun getCookies(steamId: Long): List<NetworkCookie>

    suspend fun deleteCookies(steamId: Long)
}
