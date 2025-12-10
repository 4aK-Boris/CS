package dmitriy.losev.cs.dto.steam

import dmitriy.losev.cs.EMPTY_STRING
import java.time.Instant

data class ActiveSteamAccountDTO(
    val steamId: Long,
    val marketCSGOApiToken: String,
    val accessToken: String = EMPTY_STRING,
    val refreshToken: String = EMPTY_STRING,
    val sessionId: String = EMPTY_STRING,
    val createdAt: Instant = Instant.now()
)
