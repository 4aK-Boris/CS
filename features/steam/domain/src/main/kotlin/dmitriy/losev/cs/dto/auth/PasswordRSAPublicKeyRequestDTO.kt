package dmitriy.losev.cs.dto.auth

data class PasswordRSAPublicKeyRequestDTO(
    val steamId: Long,
    val login: String
)
