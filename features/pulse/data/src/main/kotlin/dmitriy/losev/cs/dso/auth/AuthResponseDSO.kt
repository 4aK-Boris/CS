package dmitriy.losev.cs.dso.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseDSO(

    @SerialName(value = "authData")
    val authData: AuthDataDSO,

    @SerialName(value = "twoFaData")
    val twoFactorData: String?,

    @SerialName(value = "isEmailConfirmed")
    val isEmailConfirmed: Boolean,
)
