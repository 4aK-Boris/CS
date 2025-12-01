package dmitriy.losev.cs.handlers

import dmitriy.losev.cs.Database
import dmitriy.losev.cs.cookie.NetworkCookie
import dmitriy.losev.cs.tables.CookiesTable
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.r2dbc.batchInsert
import org.jetbrains.exposed.v1.r2dbc.deleteWhere
import org.jetbrains.exposed.v1.r2dbc.selectAll

internal class CookieHandlerImpl(private val json: Json, private val database: Database) : CookieHandler {

    override suspend fun saveCookies(steamId: ULong, cookies: List<NetworkCookie>): Unit = database.suspendTransaction {

        CookiesTable.deleteWhere { CookiesTable.steamId eq steamId }

        if (cookies.isNotEmpty()) {
            CookiesTable.batchInsert(data = cookies) { cookie ->
                set(column = CookiesTable.steamId, value = cookie.steamId)
                set(column = CookiesTable.name, value = cookie.name)
                set(column = CookiesTable.value, value = cookie.value)
                set(column = CookiesTable.encoding, value = cookie.encoding)
                set(column = CookiesTable.maxAge, value = cookie.maxAge)
                set(column = CookiesTable.expires, value = cookie.expires)
                set(column = CookiesTable.domain, value = cookie.domain)
                set(column = CookiesTable.path, value = cookie.path)
                set(column = CookiesTable.secure, value = cookie.secure)
                set(column = CookiesTable.httpOnly, value = cookie.httpOnly)
                set(column = CookiesTable.extensions, value = json.encodeToString(cookie.extensions))
            }
        }
    }

    override suspend fun getCookies(steamId: ULong): List<NetworkCookie> = database.suspendTransaction {
        CookiesTable
            .selectAll()
            .where { CookiesTable.steamId eq steamId }
            .map(transform = ::convertToNetworkCookie)
            .toList()
    }

    override suspend fun deleteCookies(steamId: ULong): Unit = database.suspendTransaction {
        CookiesTable.deleteWhere { CookiesTable.steamId eq steamId }
    }

    private fun convertToNetworkCookie(resultRow: ResultRow): NetworkCookie {

        val extensionsJson = resultRow.get(expression = CookiesTable.extensions)
        val extensions = json.decodeFromString<Map<String, String?>>(extensionsJson)

        return NetworkCookie(
            steamId = resultRow.get(expression = CookiesTable.steamId),
            name = resultRow.get(expression = CookiesTable.name),
            value = resultRow.get(expression = CookiesTable.value),
            encoding = resultRow.get(expression = CookiesTable.encoding),
            maxAge = resultRow.getOrNull(expression = CookiesTable.maxAge),
            expires = resultRow.getOrNull(expression = CookiesTable.expires),
            domain = resultRow.getOrNull(expression = CookiesTable.domain),
            path = resultRow.getOrNull(expression = CookiesTable.path),
            secure = resultRow.get(expression = CookiesTable.secure),
            httpOnly = resultRow.get(expression = CookiesTable.httpOnly),
            extensions = extensions
        )
    }
}
