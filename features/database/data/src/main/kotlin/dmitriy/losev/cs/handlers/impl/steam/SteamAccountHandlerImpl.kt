package dmitriy.losev.cs.handlers.impl.steam

import dmitriy.losev.cs.Database
import dmitriy.losev.cs.dso.steam.ActiveSteamAccountDSO
import dmitriy.losev.cs.dso.steam.SteamAccountDSO
import dmitriy.losev.cs.dso.steam.UpsertActiveSteamAccountResponseDSO
import dmitriy.losev.cs.dso.steam.UpsertSteamAccountResponseDSO
import dmitriy.losev.cs.handlers.steam.SteamAccountHandler
import dmitriy.losev.cs.tables.steam.ActiveSteamAccountsTable
import dmitriy.losev.cs.tables.steam.SteamAccountsTable
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.r2dbc.deleteWhere
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

    override suspend fun upsertActiveSteamAccount(activeSteamAccount: ActiveSteamAccountDSO): UpsertActiveSteamAccountResponseDSO? = database.suspendTransaction {
        ActiveSteamAccountsTable
            .upsertReturning(returning = listOf(ActiveSteamAccountsTable.steamId)) { upsertStatement ->
                upsertStatement.set(column = ActiveSteamAccountsTable.steamId, value = activeSteamAccount.steamId)
                upsertStatement.set(column = ActiveSteamAccountsTable.marketCSGOApiToken, value = activeSteamAccount.marketCSGOApiToken)
                upsertStatement.set(column = ActiveSteamAccountsTable.accessToken, value = activeSteamAccount.accessToken)
                upsertStatement.set(column = ActiveSteamAccountsTable.refreshToken, value = activeSteamAccount.refreshToken)
                upsertStatement.set(column = ActiveSteamAccountsTable.sessionId, value = activeSteamAccount.sessionId)
                upsertStatement.set(column = ActiveSteamAccountsTable.createdAt, value = activeSteamAccount.createdAt)
            }
            .map(transform = ::convertToUpsertActiveSteamAccountResponse)
            .firstOrNull()
    }

    override suspend fun deleteSteamAccount(steamId: Long): Unit = database.suspendTransaction {
        SteamAccountsTable.deleteWhere { SteamAccountsTable.steamId eq steamId }
    }

    override suspend fun deleteActiveSteamAccount(steamId: Long) {
        ActiveSteamAccountsTable.deleteWhere { ActiveSteamAccountsTable.steamId eq steamId }
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

    override suspend fun getActiveSteamAccountBySteamId(steamId: Long): ActiveSteamAccountDSO? = database.suspendTransaction {
        ActiveSteamAccountsTable
            .selectAll()
            .where { ActiveSteamAccountsTable.steamId eq steamId }
            .map(transform = ::convertToActiveSteamAccount)
            .firstOrNull()
    }

    override suspend fun getActiveSteamAccountByLogin(login: String): ActiveSteamAccountDSO? = database.suspendTransaction {
        ActiveSteamAccountsTable
            .innerJoin(otherTable = SteamAccountsTable)
            .select(ActiveSteamAccountsTable.fields)
            .where { SteamAccountsTable.login eq login }
            .map(transform = ::convertToActiveSteamAccount)
            .firstOrNull()
    }

    override suspend fun getAllSteamAccounts(): List<SteamAccountDSO> = database.suspendTransaction {
        SteamAccountsTable
            .selectAll()
            .map(transform = ::convertToSteamAccount)
            .toList()
    }

    override suspend fun getAllActiveSteamAccounts(): List<ActiveSteamAccountDSO> = database.suspendTransaction {
        ActiveSteamAccountsTable
            .selectAll()
            .map(transform = ::convertToActiveSteamAccount)
            .toList()
    }

    override suspend fun getAllActiveSteamAccountsSteamId(): List<Long> = database.suspendTransaction {
        ActiveSteamAccountsTable
            .select(ActiveSteamAccountsTable.steamId)
            .map { resultRow -> resultRow.get(expression = ActiveSteamAccountsTable.steamId) }
            .toList()
    }

    private suspend fun getLoginBySteamId(steamId: Long): String = database.suspendTransaction {
        SteamAccountsTable
            .select(SteamAccountsTable.login)
            .where { SteamAccountsTable.steamId eq steamId }
            .map { resultRow -> resultRow[SteamAccountsTable.login] }
            .first()
    }

    private suspend fun convertToUpsertSteamAccountResponse(resultRow: ResultRow): UpsertSteamAccountResponseDSO {
        return UpsertSteamAccountResponseDSO(
            steamId = resultRow.get(expression = SteamAccountsTable.steamId),
            login = resultRow.get(expression = SteamAccountsTable.login),
            createdAt = resultRow.get(expression = SteamAccountsTable.createdAt)
        )
    }

    private suspend fun convertToUpsertActiveSteamAccountResponse(resultRow: ResultRow): UpsertActiveSteamAccountResponseDSO {
        return resultRow.get(expression = ActiveSteamAccountsTable.steamId).let { steamId ->
            UpsertActiveSteamAccountResponseDSO(
                steamId = steamId,
                login = getLoginBySteamId(steamId),
                createdAt = resultRow.get(expression = SteamAccountsTable.createdAt)
            )
        }
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

    private suspend fun convertToActiveSteamAccount(resultRow: ResultRow): ActiveSteamAccountDSO {
        return ActiveSteamAccountDSO(
            steamId = resultRow.get(expression = SteamAccountsTable.steamId),
            marketCSGOApiToken = resultRow.get(expression = ActiveSteamAccountsTable.marketCSGOApiToken),
            accessToken = resultRow.get(expression = ActiveSteamAccountsTable.accessToken),
            refreshToken = resultRow.get(expression = ActiveSteamAccountsTable.refreshToken),
            sessionId = resultRow.get(expression = ActiveSteamAccountsTable.sessionId),
            createdAt = resultRow.get(expression = SteamAccountsTable.createdAt)
        )
    }
}
