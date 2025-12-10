package dmitriy.losev.cs.cookie

import com.github.benmanes.caffeine.cache.Cache
import io.ktor.http.Cookie
import io.ktor.http.CookieEncoding
import io.ktor.util.date.GMTDate
import java.util.concurrent.ConcurrentHashMap

internal class CookieCacheUpdaterImpl(private val cache: Cache<Long, MutableMap<String, Cookie>>) : CookieCacheUpdater {

    override fun updateCache(cookies: List<NetworkCookie>) {

        if (cookies.isEmpty()) return

        val steamId = cookies.first().steamId

        val cookiesMap = cache.get(steamId) { ConcurrentHashMap() }

        cookies.forEach { networkCookie ->
            val ktorCookie = convertToKtorCookie(networkCookie)
            val key = generateCookieKey(ktorCookie)
            cookiesMap[key] = ktorCookie
        }
    }

    override fun invalidateCache(steamId: Long) {
        cache.invalidate(steamId)
    }

    private fun generateCookieKey(cookie: Cookie): String {
        val domain = cookie.domain ?: ""
        val path = cookie.path ?: "/"
        return "${domain.lowercase()}:${path}:${cookie.name}"
    }

    private fun convertToKtorCookie(cookie: NetworkCookie): Cookie {
        return Cookie(
            name = cookie.name,
            value = cookie.value,
            encoding = CookieEncoding.entries[cookie.encoding],
            maxAge = cookie.maxAge,
            expires = cookie.expires?.let { expires -> GMTDate(expires) },
            domain = cookie.domain,
            path = cookie.path,
            secure = cookie.secure,
            httpOnly = cookie.httpOnly,
            extensions = cookie.extensions
        )
    }
}
