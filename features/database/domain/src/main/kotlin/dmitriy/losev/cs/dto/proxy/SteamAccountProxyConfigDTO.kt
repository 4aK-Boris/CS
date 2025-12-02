package dmitriy.losev.cs.dto.proxy

data class SteamAccountProxyConfigDTO(
    val steamId: Long,
    val host: String,
    val port: Int,
    val login: String,
    val password: String
)
