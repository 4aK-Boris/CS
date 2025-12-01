package dmitriy.losev.cs.tables.steam

import dmitriy.losev.cs.core.androidDeviceId
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.javatime.timestamp

internal object SteamAccountsTable : Table(name = "steam.steam_accounts") {

    val steamId = long(name = "steam_id")

    val login = varchar(name = "login", length = 64)

    val password = binary(name = "password")

    val sharedSecret = binary(name = "shared_secret")

    val revocationCode = binary(name = "revocation_code")

    val identitySecret = binary(name = "identity_secret")

    val deviceId = androidDeviceId(name = "device_id")

    val createdAt = timestamp(name = "created_at")

    override val primaryKey = PrimaryKey(steamId)
}
