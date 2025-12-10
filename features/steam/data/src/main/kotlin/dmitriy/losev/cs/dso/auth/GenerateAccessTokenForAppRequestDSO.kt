package dmitriy.losev.cs.dso.auth

data class GenerateAccessTokenForAppRequestDSO(
    val steamId: Long,
    val refreshToken: String,
    val steamIdString: String = steamId.toString()
)
