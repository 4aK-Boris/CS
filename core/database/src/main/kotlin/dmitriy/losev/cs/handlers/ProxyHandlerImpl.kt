package dmitriy.losev.cs.handlers

import dmitriy.losev.cs.Database
import dmitriy.losev.cs.proxy.ProxyConfig
import dmitriy.losev.cs.proxy.SteamAccountProxyConfig
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
import org.jetbrains.exposed.v1.r2dbc.select
import org.jetbrains.exposed.v1.r2dbc.selectAll
import org.jetbrains.exposed.v1.r2dbc.updateReturning

internal class ProxyHandlerImpl(private val database: Database) : ProxyHandler {

    override suspend fun addProxyConfigs(proxyConfigs: List<ProxyConfig>): Int = database.suspendTransaction {
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
        }.count()
    }

    override suspend fun getProxyConfigs(): List<ProxyConfig> = database.suspendTransaction {
        ProxyTable
            .selectAll()
            .map(transform = ::convertToProxyConfig)
            .toList()
    }

    override suspend fun getSteamAccountProxyConfigs(): List<SteamAccountProxyConfig> = database.suspendTransaction {
        ProxyTable
            .selectAll()
            .where { ProxyTable.steamId.isNotNull() }
            .map(transform = ::convertToSteamAccountsProxyConfig)
            .toList()
    }

    override suspend fun addSteamAccountProxyConfig(steamId: Long): ProxyConfig = database.suspendTransaction {

        val firstAvailableProxy = ProxyTable
            .selectAll()
            .where { ProxyTable.steamId.isNull() }
            .limit(count = 1)
            .map { resultRow -> resultRow[ProxyTable.host] to resultRow[ProxyTable.port] }
            .firstOrNull()

        requireNotNull(firstAvailableProxy) { "No available proxy found" }

        val proxyConfig = ProxyTable.updateReturning(
            returning = listOf(ProxyTable.host),
            where = { (ProxyTable.host eq firstAvailableProxy.first) and (ProxyTable.port eq firstAvailableProxy.second) },
            body = { updateStatement ->
                updateStatement.set(column = ProxyTable.steamId, value = steamId)
            }
        )
            .map(transform = ::convertToProxyConfig)
            .firstOrNull()

        requireNotNull(proxyConfig) { "Error with get proxy config for steamId $steamId" }
    }

    override suspend fun deleteProxyConfig(host: String, port: Int): Int = database.suspendTransaction {
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

    override suspend fun hasAvailableProxy(): Boolean = database.suspendTransaction {
        ProxyTable
            .selectAll()
            .where { ProxyTable.steamId.isNull() }
            .empty()
            .not()
    }

    override suspend fun hasSteamAccountForProxy(host: String, port: Int): Boolean = database.suspendTransaction {
        ProxyTable
            .selectAll()
            .where { ((ProxyTable.host eq host) and (ProxyTable.port eq port)) and ProxyTable.steamId.isNotNull() }
            .empty()
            .not()
    }

    override suspend fun getSteamIdByProxyConfig(host: String, port: Int): Long? = database.suspendTransaction {
        ProxyTable
            .select(ProxyTable.steamId)
            .where { (ProxyTable.host eq host) and (ProxyTable.port eq port) }
            .map { resultRow -> resultRow[ProxyTable.steamId] }
            .firstOrNull()
    }

    private fun convertToProxyConfig(resultRow: ResultRow): ProxyConfig {
        return ProxyConfig.Default(
            host = resultRow.get(expression = ProxyTable.host),
            port = resultRow.get(expression = ProxyTable.port),
            login = resultRow.get(expression = ProxyTable.login),
            password = resultRow.get(expression = ProxyTable.password)
        )
    }

    private fun convertToSteamAccountsProxyConfig(resultRow: ResultRow): SteamAccountProxyConfig {

        val steamId = requireNotNull(resultRow.get(expression = ProxyTable.steamId)) {
            "SteamId cannot be null for SteamAccountsProxyConfig"
        }

        val proxyConfig = convertToProxyConfig(resultRow)

        return SteamAccountProxyConfig(
            steamId = steamId,
            proxyConfig = proxyConfig
        )
    }
}
