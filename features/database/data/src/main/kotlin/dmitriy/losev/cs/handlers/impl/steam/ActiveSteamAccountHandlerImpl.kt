package dmitriy.losev.cs.handlers.impl.steam

import dmitriy.losev.cs.Database
import dmitriy.losev.cs.dso.steam.ActiveSteamAccountDSO
import dmitriy.losev.cs.dso.steam.UpsertActiveSteamAccountResponseDSO
import dmitriy.losev.cs.handlers.steam.ActiveSteamAccountHandler
import dmitriy.losev.cs.tables.steam.ActiveSteamAccountsTable
import dmitriy.losev.cs.tables.steam.SteamAccountsTable
import kotlinx.coroutines.flow.first
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

@Singleton(binds = [ActiveSteamAccountHandler::class])
internal class ActiveSteamAccountHandlerImpl(private val database: Database) : ActiveSteamAccountHandler {

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

    override suspend fun deleteActiveSteamAccountBySteamId(steamId: Long): Long? = database.suspendTransaction {
        ActiveSteamAccountsTable
            .deleteReturning(
                returning = listOf(ActiveSteamAccountsTable.steamId),
                where = { ActiveSteamAccountsTable.steamId eq steamId }
            )
            .map(transform = ::convertActiveSteamAccountsTableToSteamId)
            .firstOrNull()
    }

    override suspend fun deleteActiveSteamAccountByLogin(login: String): Long? = database.suspendTransaction {

        val steamId = SteamAccountsTable
            .select(SteamAccountsTable.login)
            .where { SteamAccountsTable.login eq login }
            .map(transform = ::convertSteamAccountsTableToSteamId)
            .firstOrNull()

        requireNotNull(steamId) { "Couldn't find an account with login $login" }

        ActiveSteamAccountsTable
            .deleteReturning(
                returning = listOf(ActiveSteamAccountsTable.steamId),
                where = { ActiveSteamAccountsTable.steamId eq steamId  }
            )
            .map(transform = ::convertActiveSteamAccountsTableToSteamId)
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

    private fun convertSteamAccountsTableToSteamId(resultRow: ResultRow): Long {
        return resultRow.get(expression = SteamAccountsTable.steamId)
    }

    private fun convertActiveSteamAccountsTableToSteamId(resultRow: ResultRow): Long {
        return resultRow.get(expression = ActiveSteamAccountsTable.steamId)
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
