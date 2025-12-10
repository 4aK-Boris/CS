package dmitriy.losev.cs.dto.auth

data class BeginAuthSessionViaCredentialsRequestDTO(
    val steamId: Long,
    val login: String,
    val encryptedPassword: String,
    val timeStamp: Long
)
