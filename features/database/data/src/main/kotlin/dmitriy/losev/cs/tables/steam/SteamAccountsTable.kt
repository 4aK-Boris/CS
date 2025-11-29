package dmitriy.losev.cs.tables.steam

import org.jetbrains.exposed.v1.core.Table

object SteamAccountsTable : Table(name = "steam.steam_accounts") {

    val accountName = varchar(name = "account_name", length = 128)

    val sharedSecret = varchar(name = "shared_secret", length = 128)

    val serialNumber = varchar(name = "serial_number", length = 64)

    val revocationCode = varchar(name = "revocation_code", length = 16)

    val uri = varchar(name = "uri", length = 512)

    val serverTime = long(name = "server_time")

    val tokenGid = varchar(name = "token_gid", length = 64)

    val identitySecret = varchar(name = "identity_secret", length = 128)

    val secret1 = varchar(name = "secret_1", length = 128)

    val status = integer(name = "status")

    val deviceId = varchar(name = "device_id", length = 128)

    val fullyEnrolled = bool(name = "fully_enrolled")

    val steamId = ulong(name = "steam_id")

    val accessToken = text(name = "access_token")

    val refreshToken = text(name = "refresh_token")

    val sessionId = varchar(name = "session_id", length = 128).nullable()

    override val primaryKey = PrimaryKey(steamId)
}