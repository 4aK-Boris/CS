package dmitriy.losev.cs.dso.auth

data class PasswordRSAPublicKeyRequestDSO(
    val steamId: Long,
    val login: String
)
