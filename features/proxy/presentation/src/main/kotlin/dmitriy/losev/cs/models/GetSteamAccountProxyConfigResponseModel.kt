package dmitriy.losev.cs.models

import kotlinx.serialization.Serializable

@Serializable
data class GetSteamAccountProxyConfigResponseModel(
    val steamId: Long,
    val host: String,
    val port: Int,
    val login: String,
    val password: String
)
