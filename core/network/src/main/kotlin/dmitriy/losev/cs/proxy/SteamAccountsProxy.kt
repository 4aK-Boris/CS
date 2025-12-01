package dmitriy.losev.cs.proxy

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import dmitriy.losev.cs.handlers.ProxyHandler
import java.util.concurrent.TimeUnit
import org.koin.core.annotation.Provided

class SteamAccountsProxy(@Provided private val proxyHandler: ProxyHandler) {

    private val proxyCache: Cache<ULong, ProxyConfig> = Caffeine.newBuilder()
        .expireAfterWrite(5, TimeUnit.MINUTES)
        .maximumSize(10_000)
        .build()

    @Volatile
    private var initialized = false

    suspend fun initSteamAccountProxies() {
        if (initialized) return
        reloadAllProxies()
        initialized = true
    }

    suspend fun reloadAllProxies() {

        proxyHandler.getSteamAccountProxyConfigs().apply {

            proxyCache.invalidateAll()

            forEach { (steamId, proxyConfig) ->
                proxyCache.put(steamId, proxyConfig)
            }
        }
    }

    suspend fun addSteamAccountProxy(steamId: ULong) {
        val config = proxyHandler.addSteamAccountProxyConfig(steamId)
        proxyCache.put(steamId, config)
    }

    fun getSteamAccountProxyConfig(steamId: ULong): ProxyConfig {
        return requireNotNull(proxyCache.getIfPresent(steamId)) { "Proxy for steam account with steamId = $steamId does not exist" }
    }

    fun invalidate(steamId: ULong) {
        proxyCache.invalidate(steamId)
    }

    fun invalidateAll() {
        proxyCache.invalidateAll()
    }
}
