package dmitriy.losev.cs.handlers.impl.steam

import dmitriy.losev.cs.Database
import dmitriy.losev.cs.dso.steam.ActiveSteamAccountDSO
import dmitriy.losev.cs.dso.steam.UpsertActiveSteamAccountResponseDSO
import dmitriy.losev.cs.handlers.steam.ActiveSteamAccountHandler
import dmitriy.losev.cs.tables.steam.ActiveSteamAccountsTable
import dmitriy.losev.cs.tables.steam.SteamAccountsTable
import java.time.Instant
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.lessEq
import org.jetbrains.exposed.v1.r2dbc.deleteReturning
import org.jetbrains.exposed.v1.r2dbc.select
import org.jetbrains.exposed.v1.r2dbc.selectAll
import org.jetbrains.exposed.v1.r2dbc.update
import org.jetbrains.exposed.v1.r2dbc.upsertReturning
import org.koin.core.annotation.Singleton
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

@Singleton(binds = [ActiveSteamAccountHandler::class])
internal class ActiveSteamAccountHandlerImpl(private val database: Database) : ActiveSteamAccountHandler {

    override suspend fun upsertActiveSteamAccount(activeSteamAccount: ActiveSteamAccountDSO): UpsertActiveSteamAccountResponseDSO? = database.suspendTransaction {
        ActiveSteamAccountsTable
            .upsertReturning(
                ActiveSteamAccountsTable.steamId,
                returning = listOf(ActiveSteamAccountsTable.steamId, ActiveSteamAccountsTable.createdAt)
            ) { upsertStatement ->
                upsertStatement.set(column = ActiveSteamAccountsTable.steamId, value = activeSteamAccount.steamId)
                upsertStatement.set(column = ActiveSteamAccountsTable.marketCSGOApiToken, value = activeSteamAccount.marketCSGOApiToken)
                upsertStatement.set(column = ActiveSteamAccountsTable.accessToken, value = activeSteamAccount.accessToken)
                upsertStatement.set(column = ActiveSteamAccountsTable.refreshToken, value = activeSteamAccount.refreshToken)
                upsertStatement.set(column = ActiveSteamAccountsTable.sessionId, value = activeSteamAccount.sessionId)
                upsertStatement.set(column = ActiveSteamAccountsTable.createdAt, value = activeSteamAccount.createdAt)
            }
            .firstOrNull()
            ?.let { resultRow ->
                val steamId = resultRow.get(expression = ActiveSteamAccountsTable.steamId)
                val createdAt = resultRow.get(expression = ActiveSteamAccountsTable.createdAt)
                val login = getLoginBySteamId(steamId) ?: error("Couldn't find steam account with Steam ID $steamId")
                UpsertActiveSteamAccountResponseDSO(
                    steamId = steamId,
                    login = login,
                    createdAt = createdAt
                )
            }
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
                where = { ActiveSteamAccountsTable.steamId eq steamId }
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
            .map(transform = ::convertActiveSteamAccountsTableToSteamId)
            .toList()
    }

    override suspend fun getAccountsWithExpiringAccessToken(): List<Long> = database.suspendTransaction {
        ActiveSteamAccountsTable
            .select(ActiveSteamAccountsTable.steamId)
            .where { ActiveSteamAccountsTable.accessTokenUpdatedAt lessEq accessTokenThreshold }
            .map(transform = ::convertActiveSteamAccountsTableToSteamId)
            .toList()
    }

    override suspend fun getAccountsWithExpiringRefreshToken(): List<Long> = database.suspendTransaction {
        ActiveSteamAccountsTable
            .select(ActiveSteamAccountsTable.steamId)
            .where { ActiveSteamAccountsTable.refreshTokenUpdatedAt lessEq refreshTokenThreshold }
            .map(transform = ::convertActiveSteamAccountsTableToSteamId)
            .toList()
    }

    override suspend fun getAccountsWithExpiringCsFloatToken(): List<Long> = database.suspendTransaction {
        ActiveSteamAccountsTable
            .select(ActiveSteamAccountsTable.steamId)
            .where { ActiveSteamAccountsTable.csFloatTokenUpdatedAt lessEq csFloatTokenThreshold }
            .map(transform = ::convertActiveSteamAccountsTableToSteamId)
            .toList()
    }

    override suspend fun getAccountRefreshToken(steamId: Long): ByteArray? = database.suspendTransaction {
        ActiveSteamAccountsTable
            .select(ActiveSteamAccountsTable.refreshToken)
            .where { ActiveSteamAccountsTable.steamId eq steamId }
            .map(transform = ::convertActiveSteamAccountsTableToRefreshToken)
            .firstOrNull()
    }

    override suspend fun updateAccessToken(steamId: Long, accessToken: String): Unit = database.suspendTransaction {
        ActiveSteamAccountsTable
            .update(
                where = { ActiveSteamAccountsTable.steamId eq steamId }
            ) { updateStatement ->
                updateStatement.set(column = ActiveSteamAccountsTable.accessToken, value = accessToken)
                updateStatement.set(column = ActiveSteamAccountsTable.accessTokenUpdatedAt, value = currentDateTime)
            }
    }

    override suspend fun updateRefreshToken(steamId: Long, refreshToken: ByteArray): Unit = database.suspendTransaction {
        ActiveSteamAccountsTable
            .update(
                where = { ActiveSteamAccountsTable.steamId eq steamId }
            ) { updateStatement ->
                updateStatement.set(column = ActiveSteamAccountsTable.refreshToken, value = refreshToken)
                updateStatement.set(column = ActiveSteamAccountsTable.refreshTokenUpdatedAt, value = currentDateTime)
            }
    }

    override suspend fun updateCsFloatToken(steamId: Long): Unit = database.suspendTransaction {
        ActiveSteamAccountsTable
            .update(
                where = { ActiveSteamAccountsTable.steamId eq steamId }
            ) { updateStatement ->
                updateStatement.set(column = ActiveSteamAccountsTable.csFloatTokenUpdatedAt, value = currentDateTime)
            }
    }

    private val currentDateTime: Instant
        get() = Instant.now()

    private val accessTokenThreshold: Instant
        get() = currentDateTime.minus(24.hours - 5.minutes)

    private val refreshTokenThreshold: Instant
        get() = currentDateTime.minus(195.days)

    private val csFloatTokenThreshold: Instant
        get() = currentDateTime.minus(5.days - 1.hours)

    private fun Instant.minus(duration: Duration): Instant = minusMillis(duration.inWholeMilliseconds)

    private suspend fun getLoginBySteamId(steamId: Long): String? {
        return SteamAccountsTable
            .select(SteamAccountsTable.login)
            .where { SteamAccountsTable.steamId eq steamId }
            .map { resultRow -> resultRow[SteamAccountsTable.login] }
            .firstOrNull()
    }

    private fun convertSteamAccountsTableToSteamId(resultRow: ResultRow): Long {
        return resultRow.get(expression = SteamAccountsTable.steamId)
    }

    private fun convertActiveSteamAccountsTableToSteamId(resultRow: ResultRow): Long {
        return resultRow.get(expression = ActiveSteamAccountsTable.steamId)
    }

    private fun convertActiveSteamAccountsTableToRefreshToken(resultRow: ResultRow): ByteArray {
        return resultRow.get(expression = ActiveSteamAccountsTable.refreshToken)
    }

    private fun convertToActiveSteamAccount(resultRow: ResultRow): ActiveSteamAccountDSO {
        return ActiveSteamAccountDSO(
            steamId = resultRow.get(expression = ActiveSteamAccountsTable.steamId),
            marketCSGOApiToken = resultRow.get(expression = ActiveSteamAccountsTable.marketCSGOApiToken),
            accessToken = resultRow.get(expression = ActiveSteamAccountsTable.accessToken),
            refreshToken = resultRow.get(expression = ActiveSteamAccountsTable.refreshToken),
            sessionId = resultRow.get(expression = ActiveSteamAccountsTable.sessionId),
            createdAt = resultRow.get(expression = ActiveSteamAccountsTable.createdAt)
        )
    }
}
