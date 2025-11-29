package dmitriy.losev.cs.proxy

import dmitriy.losev.cs.handlers.ProxyHandler
import org.koin.core.annotation.Provided

class SteamAccountsProxy(@Provided private val proxyHandler: ProxyHandler) {

    private val steamAccountsProxyConfigsMap = mutableMapOf<ULong, ProxyConfig>()

    suspend fun initSteamAccountProxies() {
        setSteamAccountsProxy(steamAccountProxyConfigs = proxyHandler.getSteamAccountProxyConfigs())
    }

    suspend fun addSteamAccountProxy(steamId: ULong) {
        setSteamAccountsProxy(steamAccountProxyConfigs = proxyHandler.getSteamAccountProxyConfigs())
    }

    fun getSteamAccountProxyConfig(steamId: ULong): ProxyConfig {
        return requireNotNull(value = steamAccountsProxyConfigsMap[steamId]) { "Proxy for steam account with steamId = $steamId does not exist" }
    }

    private fun setSteamAccountsProxy(steamAccountProxyConfigs: List<SteamAccountsProxyConfig>) {
        steamAccountProxyConfigs.forEach { (steamId, proxyConfig) ->
            steamAccountsProxyConfigsMap[steamId] = proxyConfig
        }
    }
}