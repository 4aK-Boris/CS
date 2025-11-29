package dmitriy.losev.cs.cookie

interface CookieStorageHandler {

    suspend fun saveCookies(cookies: List<NetworkCookie>)

    suspend fun getCookies(): List<NetworkCookie>
}
