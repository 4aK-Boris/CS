package dmitriy.losev.cs.di

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import dmitriy.losev.cs.Context
import dmitriy.losev.cs.clients.HttpClientHandler
import dmitriy.losev.cs.cookie.CookieCacheUpdater
import dmitriy.losev.cs.cookie.CookieCacheUpdaterImpl
import dmitriy.losev.cs.cookie.CookieStorageHandlerFactory
import dmitriy.losev.cs.handlers.CookieHandler
import dmitriy.losev.cs.handlers.ProxyHandler
import io.ktor.http.Cookie
import java.util.concurrent.TimeUnit
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Singleton

@Module(includes = [NetworkClientModule::class])
class ServicesNetworkModule {

    @Singleton
    internal fun getJson(): Json {
        return Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            encodeDefaults = false
            coerceInputValues = true
            useAlternativeNames = false
        }
    }

    @Singleton
    internal fun getHttpClientHandler(
        @Provided context: Context,
        cookieStorageHandlerFactory: CookieStorageHandlerFactory,
        @Provided proxyHandler: ProxyHandler,
        @Named(value = "PersistentCookieCache") persistentCookieCache: Cache<Long, MutableMap<String, Cookie>>
    ): HttpClientHandler {
        return HttpClientHandler(context, cookieStorageHandlerFactory, proxyHandler, persistentCookieCache)
    }

    @Singleton
    @Named(value = "PersistentCookieCache")
    internal fun getPersistentCookieCache(): Cache<Long, MutableMap<String, Cookie>> {
        return Caffeine.newBuilder()
            .expireAfterWrite(30, TimeUnit.DAYS)
            .maximumSize(10_000)
            .build()
    }

    @Singleton
    internal fun getCookieCacheUpdater(
        @Named(value = "PersistentCookieCache") persistentCookieCache: Cache<Long, MutableMap<String, Cookie>>,
        @Provided cookieHandler: CookieHandler
    ): CookieCacheUpdater {
        return CookieCacheUpdaterImpl(persistentCookieCache).apply {
            cookieHandler.setCookieCacheUpdater(this)
        }
    }
}
