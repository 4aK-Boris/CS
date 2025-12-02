package dmitriy.losev.cs.plugins

import com.github.benmanes.caffeine.cache.Cache
import dmitriy.losev.cs.cookie.CookieStorageHandlerFactory
import dmitriy.losev.cs.cookie.PersistentCookiesStorage
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.http.Cookie

internal fun HttpClientConfig<OkHttpConfig>.configureCookie(
    steamId: Long,
    cookieStorageHandlerFactory: CookieStorageHandlerFactory,
    cache: Cache<Long, MutableMap<String, Cookie>>
) {
    install(plugin = HttpCookies) {
        storage = PersistentCookiesStorage(cookieStorageHandlerFactory, steamId, cache)
    }
}
