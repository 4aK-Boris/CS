package dmitriy.losev.cs.dso.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerateAccessTokenForAppResponseDSO(

    @SerialName(value = "access_token")
    val accessToken: String
)
