package dmitriy.losev.cs.clients

import com.github.benmanes.caffeine.cache.Cache
import dmitriy.losev.cs.Context
import dmitriy.losev.cs.cookie.CookieStorageHandlerFactory
import dmitriy.losev.cs.plugins.configureClient
import dmitriy.losev.cs.plugins.configureCookie
import dmitriy.losev.cs.plugins.configureEncoding
import dmitriy.losev.cs.plugins.configureJson
import dmitriy.losev.cs.plugins.configureLogging
import dmitriy.losev.cs.plugins.configureProxy
import dmitriy.losev.cs.plugins.configureRequestRetry
import dmitriy.losev.cs.plugins.configureResponseValidation
import dmitriy.losev.cs.plugins.configureTimeout
import dmitriy.losev.cs.proxy.SteamAccountsProxy
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.http.Cookie
import org.koin.core.annotation.Named
import org.koin.core.annotation.Provided

class HttpClientHandler(
    @Provided private val context: Context,
    private val cookieStorageHandlerFactory: CookieStorageHandlerFactory,
    private val steamAccountsProxy: SteamAccountsProxy,
    @Named("PersistentCookieCache") private val persistentCookieCache: Cache<ULong, MutableMap<String, Cookie>>
) {

    val httpClient = HttpClient(OkHttp) {
        configureJson()
        configureCookie(steamId = context.steamConfig.defaultSteamId, cookieStorageHandlerFactory = cookieStorageHandlerFactory, cache = persistentCookieCache)
        configureTimeout(context)
        configureEncoding()
        configureRequestRetry()
        configureLogging()
        configureResponseValidation()
        configureClient()
    }

    fun getProxyHttpClient(steamId: ULong): HttpClient = HttpClient(OkHttp) {

        val proxyConfig = steamAccountsProxy.getSteamAccountProxyConfig(steamId)

        configureJson()
        configureCookie(steamId, cookieStorageHandlerFactory, persistentCookieCache)
        configureTimeout(context)
        configureEncoding()
        configureLogging()
        configureResponseValidation()
        configureProxy(proxyConfig)
        configureClient()
    }

    fun getProxyHttpClient(proxyConfig: ProxyConfig, steamId: ULong = context.steamConfig.defaultSteamId): HttpClient = HttpClient(OkHttp) {

        configureJson()
        configureCookie(steamId, cookieStorageHandlerFactory, persistentCookieCache)
        configureTimeout(context)
        configureEncoding()
        configureLogging()
        configureResponseValidation()
        configureProxy(proxyConfig)
        configureClient()
    }
}