package dmitriy.losev.cs.dto.auth

data class GenerateAccessTokenForAppRequestDTO(
    val steamId: Long,
    val refreshToken: String
)
