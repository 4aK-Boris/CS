package dmitriy.losev.cs.repositories.proxy

import dmitriy.losev.cs.dto.proxy.ProxyConfigDTO
import dmitriy.losev.cs.dto.proxy.SteamAccountProxyConfigDTO
import dmitriy.losev.cs.handlers.ProxyHandler
import dmitriy.losev.cs.mappers.proxy.ProxyConfigMapper
import dmitriy.losev.cs.mappers.proxy.SteamAccountProxyConfigMapper
import dmitriy.losev.cs.proxy.ProxyConfig
import org.koin.core.annotation.Factory

@Factory(binds = [ProxyDatabaseRepository::class])
class ProxyDatabaseRepositoryImpl(
    private val proxyHandler: ProxyHandler,
    private val proxyConfigMapper: ProxyConfigMapper,
    private val steamAccountProxyConfigMapper: SteamAccountProxyConfigMapper
): ProxyDatabaseRepository {

    override suspend fun upsertProxyConfigs(proxyConfigs: List<ProxyConfigDTO>): Int {
        return proxyHandler.addProxyConfigs(proxyConfigs = proxyConfigs.map(transform = proxyConfigMapper::map))
    }

    override suspend fun getProxyConfigs(): List<ProxyConfigDTO> {
        return proxyHandler.getProxyConfigs().map(transform = proxyConfigMapper::map)
    }

    override suspend fun getSteamAccountProxyConfigs(): List<SteamAccountProxyConfigDTO> {
        return proxyHandler.getSteamAccountProxyConfigs().map(transform = steamAccountProxyConfigMapper::map)
    }

    override suspend fun addSteamAccountProxyConfig(steamId: Long): ProxyConfigDTO {
        return proxyHandler.addSteamAccountProxyConfig(steamId).toDTO()
    }

    override suspend fun deleteProxyConfig(host: String, port: Int): Int {
        return proxyHandler.deleteProxyConfig(host, port)
    }

    override suspend fun getNumberOfAvailableProxies(): Int {
        return proxyHandler.getNumberOfAvailableProxies()
    }

    override suspend fun hasAvailableProxy(): Boolean {
        return proxyHandler.hasAvailableProxy()
    }

    override suspend fun hasSteamAccountForProxy(host: String, port: Int): Boolean {
        return proxyHandler.hasSteamAccountForProxy(host, port)
    }

    override suspend fun getSteamIdByProxyConfig(host: String, port: Int): Long? {
        return proxyHandler.getSteamIdByProxyConfig(host, port)
    }

    private fun ProxyConfig.toDTO(): ProxyConfigDTO = proxyConfigMapper.map(value = this)
}
