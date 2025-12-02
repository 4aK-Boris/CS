package dmitriy.losev.cs.tables

import org.jetbrains.exposed.v1.core.Table

object ProxyTable: Table(name = "network.proxies") {

    val host = varchar(name = "host", length = 32)

    val port = integer(name = "port")

    val login = binary(name = "login")

    val password = binary(name = "password")

    val steamId = long(name = "steam_id").nullable()

    override val primaryKey = PrimaryKey(host, port)
}
