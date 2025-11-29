package dmitriy.losev.cs.dso.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthRequestDSO(

    @SerialName(value = "login")
    val email: String,

    @SerialName(value = "password")
    val password: String
)