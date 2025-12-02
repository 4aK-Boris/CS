package dmitriy.losev.cs.dso.steam

import java.time.Instant

data class UpsertActiveSteamAccountResponseDSO(
    val steamId: Long,
    val login: String,
    val createdAt: Instant
)
