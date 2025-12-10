package dmitriy.losev.cs.dso.auth

data class UpdateAuthSessionWithSteamGuardCodeRequestDSO(
    val clientId: String,
    val steamId: Long,
    val steamGuardCode: String,
    val codeType: String,
    val steamIdString: String = steamId.toString()
)
