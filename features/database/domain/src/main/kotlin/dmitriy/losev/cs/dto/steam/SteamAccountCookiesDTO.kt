package dmitriy.losev.cs.dto.steam

import java.time.LocalDateTime

data class SteamAccountCookiesDTO(
    val steamId: ULong,
    val steamLoginSecure: String,
    val sessionId: String,
    val expires: LocalDateTime
)
