package dmitriy.losev.cs.clients

import com.github.benmanes.caffeine.cache.Cache
import dmitriy.losev.cs.Context
import dmitriy.losev.cs.cookie.CookieStorageHandlerFactory
import dmitriy.losev.cs.handlers.ProxyHandler
import dmitriy.losev.cs.plugins.configureClient
import dmitriy.losev.cs.plugins.configureCookie
import dmitriy.losev.cs.plugins.configureEncoding
import dmitriy.losev.cs.plugins.configureJson
import dmitriy.losev.cs.plugins.configureLogging
import dmitriy.losev.cs.plugins.configureProxy
import dmitriy.losev.cs.plugins.configureRequestRetry
import dmitriy.losev.cs.plugins.configureResponseValidation
import dmitriy.losev.cs.plugins.configureTimeout
import dmitriy.losev.cs.proxy.ProxyConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.http.Cookie

class HttpClientHandler(
    private val context: Context,
    private val cookieStorageHandlerFactory: CookieStorageHandlerFactory,
    private val proxyHandler: ProxyHandler,
    private val persistentCookieCache: Cache<Long, MutableMap<String, Cookie>>
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

    fun getMobileProxyHttpClient(steamId: Long, proxyConfig: ProxyConfig): HttpClient {

        return HttpClient(OkHttp) {

            followRedirects = false

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

    suspend fun getProxyHttpClient(steamId: Long): HttpClient {

        val proxyConfig = proxyHandler.getProxyConfigBySteamId(steamId) ?: error("Proxy for steam account with steamId = $steamId does not exist")

        return HttpClient(OkHttp) {

            followRedirects = false

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

    fun getProxyHttpClient(proxyConfig: ProxyConfig, steamId: Long = context.steamConfig.defaultSteamId): HttpClient = HttpClient(OkHttp) {

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
