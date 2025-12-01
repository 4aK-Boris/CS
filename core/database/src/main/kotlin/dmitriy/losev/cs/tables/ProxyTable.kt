package dmitriy.losev.cs.tables

import org.jetbrains.exposed.v1.core.Table

object ProxyTable: Table(name = "network.proxies") {

    val host = varchar(name = "host", length = 32)

    val port = integer(name = "port")

    val login = varchar(name = "login", length = 32)

    val password = varchar(name = "password", length = 32)

    val steamId = ulong(name = "steam_id").nullable()

    override val primaryKey = PrimaryKey(host, port)
}
