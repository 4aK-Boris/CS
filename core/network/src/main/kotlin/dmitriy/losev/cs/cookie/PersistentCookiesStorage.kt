package dmitriy.losev.cs.cookie

import com.github.benmanes.caffeine.cache.Cache
import io.ktor.client.plugins.cookies.CookiesStorage
import io.ktor.http.Cookie
import io.ktor.http.CookieEncoding
import io.ktor.http.Url
import io.ktor.util.date.GMTDate
import io.ktor.util.date.getTimeMillis
import java.util.concurrent.ConcurrentHashMap
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class PersistentCookiesStorage(
    cookieStorageHandlerFactory: CookieStorageHandlerFactory,
    private val steamId: ULong,
    private val cache: Cache<ULong, MutableMap<String, Cookie>>,
) : CookiesStorage {

    private val loadMutex = Mutex()

    private val cookieStorageHandler = cookieStorageHandlerFactory.create(steamId)

    @Volatile
    private var isLoaded = false

    override suspend fun get(requestUrl: Url): List<Cookie> {

        ensureLoaded()

        val cookiesMap = cache.getIfPresent(steamId) ?: return emptyList()

        return cookiesMap.values
            .filter { cookie -> cookie.matches(requestUrl) && isExpired(cookie).not() }
            .toList()
    }

    override suspend fun addCookie(requestUrl: Url, cookie: Cookie) {

        ensureLoaded()

        val cookiesMap = cache.get(steamId) { ConcurrentHashMap() }

        val key = generateCookieKey(cookie, requestUrl)

        cookiesMap[key] = cookie

        saveCookiesToStorage()
    }

    override fun close() {

        cache.invalidate(steamId)

        isLoaded = false
    }

    private suspend fun ensureLoaded() {

        if (isLoaded) return

        loadMutex.withLock {
            if (!isLoaded) {
                loadCookiesFromStorage()
                isLoaded = true
            }
        }
    }

    private suspend fun loadCookiesFromStorage() {

        val networkCookies = cookieStorageHandler.getCookies()

        val cookiesMap = ConcurrentHashMap<String, Cookie>()

        networkCookies
            .map(transform = ::convertToKtorCookie)
            .filterNot(predicate = ::isExpired)
            .forEach { cookie ->
                val key = generateCookieKey(cookie, null)
                cookiesMap[key] = cookie
            }

        cache.put(steamId, cookiesMap)
    }

    private suspend fun saveCookiesToStorage() {

        val cookiesMap = cache.getIfPresent(steamId) ?: return

        val expiredKeys = cookiesMap.filter { (_, cookie) -> isExpired(cookie) }.keys

        expiredKeys.forEach { url -> cookiesMap.remove(url) }

        val networkCookies = cookiesMap.values.map(transform = ::convertToNetworkCookie)

        cookieStorageHandler.saveCookies(networkCookies)
    }

    private fun generateCookieKey(cookie: Cookie, requestUrl: Url?): String {
        val domain = cookie.domain ?: requestUrl?.host ?: ""
        val path = cookie.path ?: "/"
        return "${domain.lowercase()}:${path}:${cookie.name}"
    }

    private fun isExpired(cookie: Cookie): Boolean {
        val expiresTimestamp = cookie.expires?.timestamp ?: return false
        return expiresTimestamp < getTimeMillis()
    }

    private fun Cookie.matches(url: Url): Boolean {
        if (isExpired(this)) {
            return false
        }

        if (!matchesDomain(url.host)) {
            return false
        }

        if (!matchesPath(url.encodedPath)) {
            return false
        }

        if (secure && url.protocol.name != "https") {
            return false
        }

        return true
    }

    private fun Cookie.matchesDomain(host: String): Boolean {
        val cookieDomain = domain?.lowercase()?.trimStart('.') ?: return true
        val hostLowercase = host.lowercase()

        return hostLowercase == cookieDomain || hostLowercase.endsWith(".$cookieDomain")
    }

    private fun Cookie.matchesPath(requestPath: String): Boolean {
        val cookiePath = path ?: "/"

        if (requestPath == cookiePath) {
            return true
        }

        if (!requestPath.startsWith(cookiePath)) {
            return false
        }

        return cookiePath.endsWith('/') || requestPath.getOrNull(cookiePath.length) == '/'
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

    private fun convertToNetworkCookie(cookie: Cookie): NetworkCookie {
        return NetworkCookie(
            steamId = steamId,
            name = cookie.name,
            value = cookie.value,
            encoding = cookie.encoding.ordinal,
            maxAge = cookie.maxAge,
            expires = cookie.expires?.timestamp,
            domain = cookie.domain,
            path = cookie.path,
            secure = cookie.secure,
            httpOnly = cookie.httpOnly,
            extensions = cookie.extensions
        )
    }
}
