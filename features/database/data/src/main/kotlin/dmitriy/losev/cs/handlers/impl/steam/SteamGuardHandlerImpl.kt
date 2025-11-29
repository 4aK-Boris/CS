package dmitriy.losev.cs.handlers.impl.steam

import dmitriy.losev.cs.Database
import dmitriy.losev.cs.core.Database
import dmitriy.losev.cs.handlers.steam.SteamGuardHandler
import dmitriy.losev.cs.tables.steam.SteamAccountCookiesTable
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.r2dbc.selectAll
import org.jetbrains.exposed.v1.r2dbc.upsertReturning
import org.koin.core.annotation.Factory

@Factory(binds = [SteamGuardHandler::class])
internal class SteamGuardHandlerImpl(private val database: Database) : SteamGuardHandler {

    override suspend fun insertSteamAccountCookies(steamAccountCookie: SteamAccountCookiesDSO): SteamAccountCookiesDSO? = database.suspendTransaction {
        SteamAccountCookiesTable
            .upsertReturning(SteamAccountCookiesTable.steamId) { upsertStatement ->
                upsertStatement.set(column = SteamAccountCookiesTable.steamLoginSecure, value = steamAccountCookie.steamLoginSecure)
                upsertStatement.set(column = SteamAccountCookiesTable.sessionId, value = steamAccountCookie.sessionId)
                upsertStatement.set(column = SteamAccountCookiesTable.expires, value = steamAccountCookie.expires)
            }
            .map(transform = ::convertToSteamAccountCookies)
            .firstOrNull()
    }

    override suspend fun getSteamAccountCookies(steamId: ULong): SteamAccountCookiesDSO? = database.suspendTransaction {
        SteamAccountCookiesTable
            .selectAll()
            .where { SteamAccountCookiesTable.steamId eq steamId }
            .map(transform = ::convertToSteamAccountCookies)
            .firstOrNull()
    }

    private fun convertToSteamAccountCookies(resultRow: ResultRow): SteamAccountCookiesDSO? {
        return SteamAccountCookiesDSO(
            steamId = resultRow[SteamAccountCookiesTable.steamId],
            steamLoginSecure = resultRow[SteamAccountCookiesTable.steamLoginSecure],
            sessionId = resultRow[SteamAccountCookiesTable.sessionId],
            expires = resultRow[SteamAccountCookiesTable.expires]
        )
    }
}