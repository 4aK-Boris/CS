package dmitriy.losev.cs.tables.steam

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.javatime.datetime

object SteamAccountCookiesTable: Table("steam_account_cookies") {

    val steamId = ulong(name = "steam_id")

    val steamLoginSecure = varchar(name = "steam_login_secure", length = 1024)

    val sessionId = varchar(name = "session_id", length = 24)

    val expires = datetime(name = "expires")

    override val primaryKey = PrimaryKey(steamId)
}