package dmitriy.losev.cs.di

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import dmitriy.losev.cs.Context
import dmitriy.losev.cs.clients.HttpClientHandler
import dmitriy.losev.cs.cookie.CookieStorageHandlerFactory
import dmitriy.losev.cs.plugins.configureCookie
import dmitriy.losev.cs.plugins.configureEncoding
import dmitriy.losev.cs.plugins.configureJson
import dmitriy.losev.cs.plugins.configureLogging
import dmitriy.losev.cs.plugins.configureRequestRetry
import dmitriy.losev.cs.plugins.configureResponseValidation
import dmitriy.losev.cs.plugins.configureTimeout
import dmitriy.losev.cs.proxy.ProxyClients
import dmitriy.losev.cs.proxy.SteamAccountsProxy
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.ProxyBuilder
import io.ktor.client.engine.http
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.http.Cookie
import java.util.concurrent.TimeUnit
import kotlinx.serialization.json.Json
import okhttp3.ConnectionPool
import okhttp3.ConnectionSpec
import okhttp3.Dispatcher
import okhttp3.Protocol
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
    internal fun getSteamAccountsProxy(): SteamAccountsProxy {
        return SteamAccountsProxy()
    }

    @Singleton
    internal fun getHttpClientHandler(
        @Provided context: Context,
        cookieStorageHandlerFactory: CookieStorageHandlerFactory,
        steamAccountsProxy: SteamAccountsProxy,
        @Named(value = "PersistentCookieCache") persistentCookieCache: Cache<ULong, MutableMap<String, Cookie>>
    ): HttpClientHandler {
        return HttpClientHandler(context, cookieStorageHandlerFactory, steamAccountsProxy, persistentCookieCache)
    }

    @Singleton
    internal fun getProxyClients(@Provided context: Context, httpClientHandler: HttpClientHandler): ProxyClients {
        return ProxyClients(context, httpClientHandler)
    }
}