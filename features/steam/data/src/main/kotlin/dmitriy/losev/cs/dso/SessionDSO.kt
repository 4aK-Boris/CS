package dmitriy.losev.cs.dso

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SessionDSO(

    @SerialName(value = "SteamID")
    val steamId: Long,

    @SerialName(value = "AccessToken")
    val accessToken: String,

    @SerialName(value = "RefreshToken")
    val refreshToken: String,

    @SerialName(value = "SessionID")
    val sessionId: String? = null
)
