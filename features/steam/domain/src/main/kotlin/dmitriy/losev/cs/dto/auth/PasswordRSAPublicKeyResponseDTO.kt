package dmitriy.losev.cs.dto.auth

data class PasswordRSAPublicKeyResponseDTO(
    val publickeyModulus: String,
    val publickeyExponent: String,
    val timeStamp: Long
)
