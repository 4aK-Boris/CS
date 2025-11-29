package dmitriy.losev.cs.dto

data class SessionDTO(
    val steamId: Long,
    val accessToken: String,
    val refreshToken: String,
    val sessionId: String? = null
)
