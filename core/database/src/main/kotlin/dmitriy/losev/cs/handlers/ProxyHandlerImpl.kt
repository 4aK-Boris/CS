package dmitriy.losev.cs.handlers

import dmitriy.losev.cs.Database
import dmitriy.losev.cs.proxy.ProxyConfig
import dmitriy.losev.cs.proxy.SteamAccountsProxyConfig
import dmitriy.losev.cs.tables.ProxyTable
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.isNotNull
import org.jetbrains.exposed.v1.core.isNull
import org.jetbrains.exposed.v1.r2dbc.batchInsert
import org.jetbrains.exposed.v1.r2dbc.deleteWhere
import org.jetbrains.exposed.v1.r2dbc.selectAll
import org.jetbrains.exposed.v1.r2dbc.update
import org.koin.core.annotation.Factory

@Factory(binds = [ProxyHandler::class])
class ProxyHandlerImpl(private val database: Database) : ProxyHandler {

    override suspend fun addProxies(proxyConfigs: List<ProxyConfig>): Unit = database.suspendTransaction {
        if (proxyConfigs.isNotEmpty()) {
            ProxyTable.batchInsert(
                data = proxyConfigs,
                ignore = false,
                shouldReturnGeneratedValues = false
            ) { proxyConfig ->
                set(column = ProxyTable.host, value = proxyConfig.host)
                set(column = ProxyTable.port, value = proxyConfig.port)
                set(column = ProxyTable.login, value = proxyConfig.login)
                set(column = ProxyTable.password, value = proxyConfig.password)
                set(column = ProxyTable.steamId, value = null)
            }
        }
    }

    override suspend fun getSteamAccountProxyConfigs(): List<SteamAccountsProxyConfig> = database.suspendTransaction {
        ProxyTable
            .selectAll()
            .where { ProxyTable.steamId.isNotNull() }
            .map(transform = ::convertToSteamAccountsProxyConfig)
            .toList()
    }

    override suspend fun addSteamAccountProxyConfig(steamId: ULong): Unit = database.suspendTransaction {

        val firstAvailableProxy = ProxyTable
            .selectAll()
            .where { ProxyTable.steamId.isNull() }
            .limit(count = 1)
            .map { resultRow -> resultRow[ProxyTable.host] to resultRow[ProxyTable.port] }
            .firstOrNull()

        requireNotNull(firstAvailableProxy) { "No available proxy found" }

        ProxyTable.update(
            where = { (ProxyTable.host eq firstAvailableProxy.first) and (ProxyTable.port eq firstAvailableProxy.second) }
        ) { updateStatement ->
            updateStatement.set(column = ProxyTable.steamId, value = steamId)
        }
    }

    override suspend fun deleteProxy(host: String, port: Int): Int = database.suspendTransaction {
        ProxyTable.deleteWhere {
            (ProxyTable.host eq host) and (ProxyTable.port eq port)
        }
    }

    override suspend fun getNumberOfAvailableProxies(): Int = database.suspendTransaction {
        ProxyTable
            .selectAll()
            .where { ProxyTable.steamId.isNull() }
            .count()
            .toInt()
    }

    private fun convertToSteamAccountsProxyConfig(resultRow: ResultRow): SteamAccountsProxyConfig {

        val steamId = requireNotNull(resultRow.get(expression = ProxyTable.steamId)) {
            "SteamId cannot be null for SteamAccountsProxyConfig"
        }

        val proxyConfig = ProxyConfig.Default(
            host = resultRow.get(expression = ProxyTable.host),
            port = resultRow.get(expression = ProxyTable.port),
            login = resultRow.get(expression = ProxyTable.login),
            password = resultRow.get(expression = ProxyTable.password)
        )

        return SteamAccountsProxyConfig(
            steamId = steamId,
            proxyConfig = proxyConfig
        )
    }
}