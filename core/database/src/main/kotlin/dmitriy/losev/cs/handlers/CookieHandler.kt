package dmitriy.losev.cs.handlers

import dmitriy.losev.cs.cookie.CookieCacheUpdater
import dmitriy.losev.cs.cookie.NetworkCookie

interface CookieHandler {

    fun setCookieCacheUpdater(updater: CookieCacheUpdater)

    suspend fun saveCookies(cookies: List<NetworkCookie>)

    suspend fun getCookies(steamId: Long): List<NetworkCookie>

    suspend fun deleteCookies(steamId: Long)
}
