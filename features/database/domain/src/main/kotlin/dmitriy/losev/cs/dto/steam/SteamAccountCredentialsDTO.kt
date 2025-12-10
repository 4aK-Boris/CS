package dmitriy.losev.cs.dto.steam

data class SteamAccountCredentialsDTO(
    val steamId: Long,
    val login: String,
    val password: String,
    val sharedSecret: String
)
