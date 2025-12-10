package dmitriy.losev.cs.tables.steam

import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.javatime.timestamp

internal object ActiveSteamAccountsTable : Table(name = "steam.active_steam_accounts") {

    val steamId = long(name = "steam_id").references(
        ref = SteamAccountsTable.steamId,
        onDelete = ReferenceOption.CASCADE
    )

    val marketCSGOApiToken = binary(name = "market_csgo_api_token")

    val accessToken = text(name = "access_token")

    val refreshToken = binary(name = "refresh_token")

    val sessionId = varchar(name = "session_id", length = 24)

    val createdAt = timestamp(name = "created_at")

    val accessTokenUpdatedAt = timestamp(name = "access_token_updated_at")

    val refreshTokenUpdatedAt = timestamp(name = "refresh_token_updated_at")

    val csFloatTokenUpdatedAt = timestamp(name = "cs_float_token_updated_at")

    override val primaryKey = PrimaryKey(steamId)
}
