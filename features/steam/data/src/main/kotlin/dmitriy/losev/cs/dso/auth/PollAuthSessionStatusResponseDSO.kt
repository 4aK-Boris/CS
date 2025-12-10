package dmitriy.losev.cs.dso.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PollAuthSessionStatusResponseDSO(

    @SerialName(value = "refresh_token")
    val refreshToken: String,

    @SerialName(value = "access_token")
    val accessToken: String,

    @SerialName(value = "had_remote_interaction")
    val hadRemoteInteraction: Boolean,

    @SerialName(value = "account_name")
    val login: String,
)
