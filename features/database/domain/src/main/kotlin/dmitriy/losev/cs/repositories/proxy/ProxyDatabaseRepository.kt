package dmitriy.losev.cs.repositories.proxy

import dmitriy.losev.cs.dto.proxy.ProxyConfigDTO
import dmitriy.losev.cs.dto.proxy.SteamAccountProxyConfigDTO

interface ProxyDatabaseRepository {

    suspend fun upsertProxyConfigs(proxyConfigs: List<ProxyConfigDTO>): Int

    suspend fun getProxyConfigs(): List<ProxyConfigDTO>

    suspend fun getSteamAccountProxyConfigs(): List<SteamAccountProxyConfigDTO>

    suspend fun addSteamAccountProxyConfig(steamId: Long): ProxyConfigDTO

    suspend fun deleteProxyConfig(host: String, port: Int): Int

    suspend fun getNumberOfAvailableProxies(): Int

    suspend fun hasAvailableProxy(): Boolean

    suspend fun hasSteamAccountForProxy(host: String, port: Int): Boolean

    suspend fun getSteamIdByProxyConfig(host: String, port: Int): Long?
}
