package dmitriy.losev.cs.tables

import org.jetbrains.exposed.v1.core.Table

internal object CookiesTable : Table(name = "network.cookies") {

    val steamId = long(name = "steam_id")

    val name = varchar(name = "name", length = 256)

    val value = text(name = "value")

    val encoding = integer(name = "encoding")

    val maxAge = integer(name = "max_age").nullable()

    val expires = long(name = "expires").nullable()

    val domain = varchar(name = "domain", length = 512).nullable()

    val path = varchar(name = "path", length = 512).nullable()

    val secure = bool(name = "secure")

    val httpOnly = bool(name = "http_only")

    val extensions = text(name = "extensions")

    override val primaryKey = PrimaryKey(steamId, name, domain, path)
}
