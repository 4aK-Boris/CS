package dmitriy.losev.cs.dto.auth

data class UpdateAuthSessionWithSteamGuardCodeRequestDTO(
    val clientId: String,
    val steamId: Long,
    val steamGuardCode: String,
    val codeType: Int = 3
)
