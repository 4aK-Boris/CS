package dmitriy.losev.cs.dso.auth

data class BeginAuthSessionViaCredentialsRequestDSO(
    val steamId: Long,
    val login: String,
    val encryptedPassword: String,
    val timeStamp: String
)
