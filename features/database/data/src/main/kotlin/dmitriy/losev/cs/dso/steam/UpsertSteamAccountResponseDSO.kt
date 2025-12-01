package dmitriy.losev.cs.dso.steam

import java.time.Instant

data class UpsertSteamAccountResponseDSO(
    val steamId: Long,
    val login: String,
    val createdAt: Instant
)
