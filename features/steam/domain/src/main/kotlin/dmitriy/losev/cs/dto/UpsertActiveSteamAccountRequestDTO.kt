package dmitriy.losev.cs.dto

data class UpsertActiveSteamAccountRequestDTO(
    val steamId: Long,
    val marketCSGOApiToken: String
)
