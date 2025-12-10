package dmitriy.losev.cs.dso.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PasswordRSAPublicKeyResponseDSO(

    @SerialName(value = "publickey_mod")
    val publickeyModulus: String,

    @SerialName(value = "publickey_exp")
    val publickeyExponent: String,

    @SerialName(value = "timestamp")
    val timeStamp: String
)
