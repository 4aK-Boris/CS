package dmitriy.losev.cs.handlers.impl.steam

import dmitriy.losev.cs.Database
import dmitriy.losev.cs.dso.steam.Session
import dmitriy.losev.cs.dso.steam.SteamAccountDSO
import dmitriy.losev.cs.handlers.steam.SteamAccountHandler
import dmitriy.losev.cs.tables.steam.SteamAccountsTable
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.r2dbc.deleteWhere
import org.jetbrains.exposed.v1.r2dbc.selectAll
import org.jetbrains.exposed.v1.r2dbc.upsert
import org.koin.core.annotation.Factory

@Factory(binds = [SteamAccountHandler::class])
internal class SteamAccountHandlerImpl(private val database: Database) : SteamAccountHandler {

    override suspend fun insertSteamAccount(steamAccount: SteamAccountDSO): Unit = database.suspendTransaction {
        SteamAccountsTable
            .upsert { upsertStatement ->
                upsertStatement.set(column = SteamAccountsTable.accountName, value = steamAccount.accountName)
                upsertStatement.set(column = SteamAccountsTable.sharedSecret, value = steamAccount.sharedSecret)
                upsertStatement.set(column = SteamAccountsTable.serialNumber, value = steamAccount.serialNumber)
                upsertStatement.set(column = SteamAccountsTable.revocationCode, value = steamAccount.revocationCode)
                upsertStatement.set(column = SteamAccountsTable.uri, value = steamAccount.uri)
                upsertStatement.set(column = SteamAccountsTable.serverTime, value = steamAccount.serverTime)
                upsertStatement.set(column = SteamAccountsTable.tokenGid, value = steamAccount.tokenGid)
                upsertStatement.set(column = SteamAccountsTable.identitySecret, value = steamAccount.identitySecret)
                upsertStatement.set(column = SteamAccountsTable.secret1, value = steamAccount.secret)
                upsertStatement.set(column = SteamAccountsTable.status, value = steamAccount.status)
                upsertStatement.set(column = SteamAccountsTable.deviceId, value = steamAccount.deviceId)
                upsertStatement.set(column = SteamAccountsTable.fullyEnrolled, value = steamAccount.fullyEnrolled)
                upsertStatement.set(column = SteamAccountsTable.steamId, value = steamAccount.session.steamId)
                upsertStatement.set(column = SteamAccountsTable.accessToken, value = steamAccount.session.accessToken)
                upsertStatement.set(column = SteamAccountsTable.refreshToken, value = steamAccount.session.refreshToken)
                upsertStatement.set(column = SteamAccountsTable.sessionId, value = steamAccount.session.sessionId)
            }
    }

    override suspend fun deleteSteamAccount(steamId: ULong): Unit = database.suspendTransaction {
        SteamAccountsTable.deleteWhere { SteamAccountsTable.steamId eq steamId }
    }

    override suspend fun getSteamAccountBySteamId(steamId: ULong): SteamAccountDSO? = database.suspendTransaction {
        SteamAccountsTable
            .selectAll()
            .where { SteamAccountsTable.steamId eq steamId }
            .map(transform = ::convertToSteamAccount)
            .firstOrNull()
    }

    override suspend fun getAllSteamAccounts(): List<SteamAccountDSO> = database.suspendTransaction {
        SteamAccountsTable
            .selectAll()
            .map(transform = ::convertToSteamAccount)
            .toList()
    }

    private fun convertToSteamAccount(resultRow: ResultRow): SteamAccountDSO {
        return SteamAccountDSO(
            accountName = resultRow.get(expression = SteamAccountsTable.accountName),
            sharedSecret = resultRow.get(expression = SteamAccountsTable.sharedSecret),
            serialNumber = resultRow.get(expression = SteamAccountsTable.serialNumber),
            revocationCode = resultRow.get(expression = SteamAccountsTable.revocationCode),
            uri = resultRow.get(expression = SteamAccountsTable.uri),
            serverTime = resultRow.get(expression = SteamAccountsTable.serverTime),
            tokenGid = resultRow.get(expression = SteamAccountsTable.tokenGid),
            identitySecret = resultRow.get(expression = SteamAccountsTable.identitySecret),
            secret = resultRow.get(expression = SteamAccountsTable.secret1),
            status = resultRow.get(expression = SteamAccountsTable.status),
            deviceId = resultRow.get(expression = SteamAccountsTable.deviceId),
            fullyEnrolled = resultRow.get(expression = SteamAccountsTable.fullyEnrolled),
            session = Session(
                steamId = resultRow.get(expression = SteamAccountsTable.steamId),
                accessToken = resultRow.get(expression = SteamAccountsTable.accessToken),
                refreshToken = resultRow.get(expression = SteamAccountsTable.refreshToken),
                sessionId = resultRow.getOrNull(expression = SteamAccountsTable.sessionId)
            )
        )
    }
}