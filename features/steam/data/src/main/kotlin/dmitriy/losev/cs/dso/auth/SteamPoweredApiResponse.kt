package dmitriy.losev.cs.dso.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SteamPoweredApiResponse<T: Any> (

    @SerialName(value = "response")
    val response: T
)
