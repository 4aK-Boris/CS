package dmitriy.losev.cs.dto.steam

import java.time.Instant

data class UpsertSteamAccountResponseDTO(
    val steamId: Long,
    val login: String,
    val createdAt: Instant
)
