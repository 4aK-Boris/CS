package dmitriy.losev.cs.handlers

import dmitriy.losev.cs.proxy.ProxyConfig
import dmitriy.losev.cs.proxy.SteamAccountProxyConfig

interface ProxyHandler {

    suspend fun addProxyConfigs(proxyConfigs: List<ProxyConfig>): Int

    suspend fun getProxyConfigs(): List<ProxyConfig>

    suspend fun getSteamAccountProxyConfigs(): List<SteamAccountProxyConfig>

    suspend fun getProxyConfigBySteamId(steamId: Long): ProxyConfig?

    suspend fun addSteamAccountProxyConfig(steamId: Long): ProxyConfig

    suspend fun deleteProxyConfig(host: String, port: Int): Int

    suspend fun getNumberOfAvailableProxies(): Int

    suspend fun hasAvailableProxy(): Boolean

    suspend fun hasSteamAccountForProxy(host: String, port: Int): Boolean

    suspend fun getSteamIdByProxyConfig(host: String, port: Int): Long?
}
