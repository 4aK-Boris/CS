package dmitriy.losev.cs.dso.steam


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Session(

    @SerialName(value = "AccessToken")
    val accessToken: String,

    @SerialName(value = "RefreshToken")
    val refreshToken: String,

    @SerialName(value = "SessionID")
    val sessionId: String?,

    @SerialName(value = "SteamID")
    val steamId: ULong
)