package dmitriy.losev.cs.dto.steam

import java.time.Instant

data class UpsertActiveSteamAccountResponseDTO(
    val steamId: Long,
    val login: String,
    val createdAt: Instant
)
