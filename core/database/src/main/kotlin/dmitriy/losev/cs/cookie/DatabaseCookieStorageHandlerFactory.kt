package dmitriy.losev.cs.cookie

import dmitriy.losev.cs.handlers.CookieHandler

internal class DatabaseCookieStorageHandlerFactory(private val cookieHandler: CookieHandler): CookieStorageHandlerFactory {

    override fun create(steamId: Long): CookieStorageHandler {
        return object : CookieStorageHandler {

            override suspend fun saveCookies(cookies: List<NetworkCookie>) {
                cookieHandler.saveCookies(cookies = cookies)
            }

            override suspend fun getCookies(): List<NetworkCookie> {
                return cookieHandler.getCookies(steamId = steamId)
            }
        }
    }
}
