package dmitriy.losev.cs.handlers

import dmitriy.losev.cs.proxy.ProxyConfig
import dmitriy.losev.cs.proxy.SteamAccountsProxyConfig

interface ProxyHandler {

    suspend fun addProxies(proxyConfigs: List<ProxyConfig>)

    suspend fun getSteamAccountProxyConfigs(): List<SteamAccountsProxyConfig>

    suspend fun addSteamAccountProxyConfig(steamId: ULong)

    suspend fun deleteProxy(host: String, port: Int): Int

    suspend fun getNumberOfAvailableProxies(): Int
}
