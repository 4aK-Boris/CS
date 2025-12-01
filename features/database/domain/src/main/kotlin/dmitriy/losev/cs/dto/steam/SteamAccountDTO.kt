package dmitriy.losev.cs.dto.steam

import java.time.Instant

data class SteamAccountDTO(
    val steamId: Long,
    val login: String,
    val password: String,
    val sharedSecret: String,
    val identitySecret: String,
    val revocationCode: String,
    val deviceId: String,
    val createdAt: Instant
)
