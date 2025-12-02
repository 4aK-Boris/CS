package dmitriy.losev.cs.models

import kotlinx.serialization.Serializable

@Serializable
data class GetProxyConfigResponseModel(
    val host: String,
    val port: Int,
    val login: String,
    val password: String
)
