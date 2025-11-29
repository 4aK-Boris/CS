package dmitriy.losev.cs.dto.steam

data class SteamAccountDTO(
    val accountName: String,
    val sharedSecret: String,
    val serialNumber: String,
    val revocationCode: String,
    val uri: String,
    val serverTime: Long,
    val tokenGid: String,
    val identitySecret: String,
    val secret: String,
    val status: Int,
    val deviceId: String,
    val fullyEnrolled: Boolean,
    val steamId: ULong,
    val accessToken: String,
    val refreshToken: String,
    val sessionId: String?
)
