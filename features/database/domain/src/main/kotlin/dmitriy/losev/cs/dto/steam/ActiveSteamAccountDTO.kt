package dmitriy.losev.cs.dto.steam

import java.time.Instant

data class ActiveSteamAccountDTO(
    val steamId: Long,
    val marketCSGOApiToken: String,
    val accessToken: String,
    val refreshToken: String,
    val sessionId: String,
    val createdAt: Instant
)
