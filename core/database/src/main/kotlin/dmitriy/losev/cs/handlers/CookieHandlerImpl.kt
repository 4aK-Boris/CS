package dmitriy.losev.cs.handlers

import dmitriy.losev.cs.AesCrypto
import dmitriy.losev.cs.Database
import dmitriy.losev.cs.cookie.CookieCacheUpdater
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

internal class CookieHandlerImpl(
    private val json: Json,
    private val database: Database,
    private val aesCrypto: AesCrypto
) : CookieHandler {

    private var cookieCacheUpdater: CookieCacheUpdater? = null

    override fun setCookieCacheUpdater(updater: CookieCacheUpdater) {
        cookieCacheUpdater = updater
    }

    override suspend fun saveCookies(cookies: List<NetworkCookie>): Unit = database.suspendTransaction {

            CookiesTable.deleteWhere { CookiesTable.steamId eq cookies.first().steamId }

            CookiesTable.batchInsert(data = cookies,) { cookie ->
                set(column = CookiesTable.steamId, value = cookie.steamId)
                set(column = CookiesTable.name, value = cookie.name)
                set(column = CookiesTable.value, value = aesCrypto.encrypt(data = cookie.value))
                set(column = CookiesTable.encoding, value = cookie.encoding)
                set(column = CookiesTable.maxAge, value = cookie.maxAge)
                set(column = CookiesTable.expires, value = cookie.expires)
                set(column = CookiesTable.domain, value = cookie.domain)
                set(column = CookiesTable.path, value = cookie.path)
                set(column = CookiesTable.secure, value = cookie.secure)
                set(column = CookiesTable.httpOnly, value = cookie.httpOnly)
                set(column = CookiesTable.extensions, value = json.encodeToString(cookie.extensions))
            }

        cookieCacheUpdater?.updateCache(cookies)
    }

    override suspend fun getCookies(steamId: Long): List<NetworkCookie> = database.suspendTransaction {
        CookiesTable
            .selectAll()
            .where { CookiesTable.steamId eq steamId }
            .map(transform = ::convertToNetworkCookie)
            .toList()
    }

    override suspend fun deleteCookies(steamId: Long): Unit = database.suspendTransaction {
        CookiesTable.deleteWhere { CookiesTable.steamId eq steamId }
    }

    private fun convertToNetworkCookie(resultRow: ResultRow): NetworkCookie {

        val extensionsJson = resultRow.get(expression = CookiesTable.extensions)
        val extensions = json.decodeFromString<Map<String, String?>>(extensionsJson)

        return NetworkCookie(
            steamId = resultRow.get(expression = CookiesTable.steamId),
            name = resultRow.get(expression = CookiesTable.name),
            value = aesCrypto.decrypt(encryptedData = resultRow.get(expression = CookiesTable.value)),
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
