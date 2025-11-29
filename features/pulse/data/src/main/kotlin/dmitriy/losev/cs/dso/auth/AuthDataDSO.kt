package dmitriy.losev.cs.dso.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthDataDSO(

    @SerialName(value = "token")
    val token: String,

    @SerialName(value = "email")
    val email: String
)
