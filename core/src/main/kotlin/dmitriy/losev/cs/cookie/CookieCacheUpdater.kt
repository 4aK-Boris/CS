package dmitriy.losev.cs.cookie

interface CookieCacheUpdater {

    fun updateCache(cookies: List<NetworkCookie>)

    fun invalidateCache(steamId: Long)
}
