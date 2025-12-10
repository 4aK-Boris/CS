package dmitriy.losev.cs.handlers.impl.steam

import dmitriy.losev.cs.Database
import dmitriy.losev.cs.dso.steam.SteamAccountDSO
import dmitriy.losev.cs.dso.steam.UpsertSteamAccountResponseDSO
import dmitriy.losev.cs.handlers.steam.SteamAccountHandler
import dmitriy.losev.cs.dso.steam.SteamAccountCredentialsDSO
import dmitriy.losev.cs.tables.steam.SteamAccountsTable
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.r2dbc.deleteReturning
import org.jetbrains.exposed.v1.r2dbc.select
import org.jetbrains.exposed.v1.r2dbc.selectAll
import org.jetbrains.exposed.v1.r2dbc.upsertReturning
import org.koin.core.annotation.Singleton

@Singleton(binds = [SteamAccountHandler::class])
internal class SteamAccountHandlerImpl(private val database: Database) : SteamAccountHandler {

    override suspend fun upsertSteamAccount(steamAccount: SteamAccountDSO): UpsertSteamAccountResponseDSO? = database.suspendTransaction {
        SteamAccountsTable
            .upsertReturning(
                SteamAccountsTable.steamId,
                returning = listOf(SteamAccountsTable.steamId, SteamAccountsTable.login, SteamAccountsTable.createdAt)
            ) { upsertStatement ->
                upsertStatement.set(column = SteamAccountsTable.steamId, value = steamAccount.steamId)
                upsertStatement.set(column = SteamAccountsTable.login, value = steamAccount.login)
                upsertStatement.set(column = SteamAccountsTable.password, value = steamAccount.password)
                upsertStatement.set(column = SteamAccountsTable.sharedSecret, value = steamAccount.sharedSecret)
                upsertStatement.set(column = SteamAccountsTable.identitySecret, value = steamAccount.identitySecret)
                upsertStatement.set(column = SteamAccountsTable.revocationCode, value = steamAccount.revocationCode)
                upsertStatement.set(column = SteamAccountsTable.deviceId, value = steamAccount.deviceId)
                upsertStatement.set(column = SteamAccountsTable.createdAt, value = steamAccount.createdAt)
            }
            .map(transform = ::convertToUpsertSteamAccountResponse)
            .firstOrNull()
    }

    override suspend fun deleteSteamAccountBySteamId(steamId: Long): Long? = database.suspendTransaction {
        SteamAccountsTable
            .deleteReturning(
                returning = listOf(SteamAccountsTable.steamId),
                where = { SteamAccountsTable.steamId eq steamId }
            )
            .map(transform = ::convertSteamAccountsTableToSteamId)
            .firstOrNull()
    }

    override suspend fun deleteSteamAccountByLogin(login: String): Long? = database.suspendTransaction {
        SteamAccountsTable
            .deleteReturning(
                returning = listOf(SteamAccountsTable.steamId),
                where = { SteamAccountsTable.login eq login }
            )
            .map(transform = ::convertSteamAccountsTableToSteamId)
            .firstOrNull()
    }

    override suspend fun getSteamAccountBySteamId(steamId: Long): SteamAccountDSO? = database.suspendTransaction {
        SteamAccountsTable
            .selectAll()
            .where { SteamAccountsTable.steamId eq steamId }
            .map(transform = ::convertToSteamAccount)
            .firstOrNull()
    }

    override suspend fun getSteamAccountByLogin(login: String): SteamAccountDSO? = database.suspendTransaction {
        SteamAccountsTable
            .selectAll()
            .where { SteamAccountsTable.login eq login }
            .map(transform = ::convertToSteamAccount)
            .firstOrNull()
    }

    override suspend fun getAllSteamAccounts(): List<SteamAccountDSO> = database.suspendTransaction {
        SteamAccountsTable
            .selectAll()
            .map(transform = ::convertToSteamAccount)
            .toList()
    }

    override suspend fun getSteamAccountCredentials(steamId: Long): SteamAccountCredentialsDSO? = database.suspendTransaction {
        SteamAccountsTable
            .select(SteamAccountsTable.steamId, SteamAccountsTable.login, SteamAccountsTable.password, SteamAccountsTable.sharedSecret)
            .where { SteamAccountsTable.steamId eq steamId }
            .map(transform = ::convertToSteamAccountCredentials)
            .firstOrNull()
    }

    private fun convertSteamAccountsTableToSteamId(resultRow: ResultRow): Long {
        return resultRow.get(expression = SteamAccountsTable.steamId)
    }

    private suspend fun convertToSteamAccountCredentials(resultRow: ResultRow): SteamAccountCredentialsDSO {
        return SteamAccountCredentialsDSO(
            steamId = resultRow.get(expression = SteamAccountsTable.steamId),
            login = resultRow.get(expression = SteamAccountsTable.login),
            password = resultRow.get(expression = SteamAccountsTable.password),
            sharedSecret = resultRow.get(expression = SteamAccountsTable.sharedSecret)
        )
    }

    private suspend fun convertToUpsertSteamAccountResponse(resultRow: ResultRow): UpsertSteamAccountResponseDSO {
        return UpsertSteamAccountResponseDSO(
            steamId = resultRow.get(expression = SteamAccountsTable.steamId),
            login = resultRow.get(expression = SteamAccountsTable.login),
            createdAt = resultRow.get(expression = SteamAccountsTable.createdAt)
        )
    }

    private suspend fun convertToSteamAccount(resultRow: ResultRow): SteamAccountDSO {
        return SteamAccountDSO(
            steamId = resultRow.get(expression = SteamAccountsTable.steamId),
            login = resultRow.get(expression = SteamAccountsTable.login),
            password = resultRow.get(expression = SteamAccountsTable.password),
            sharedSecret = resultRow.get(expression = SteamAccountsTable.sharedSecret),
            identitySecret = resultRow.get(expression = SteamAccountsTable.identitySecret),
            revocationCode = resultRow.get(expression = SteamAccountsTable.revocationCode),
            deviceId = resultRow.get(expression = SteamAccountsTable.deviceId),
            createdAt = resultRow.get(expression = SteamAccountsTable.createdAt)
        )
    }
}
