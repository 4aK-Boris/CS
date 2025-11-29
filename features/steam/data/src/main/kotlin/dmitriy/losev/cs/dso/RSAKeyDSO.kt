package dmitriy.losev.cs.dso

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RSAKeyDSO(

    @SerialName(value = "success")
    val success: Boolean,

    @SerialName(value = "publickey_mod")
    val publickeyModulus: String,

    @SerialName(value = "publickey_exp")
    val publickeyExponent: String,

    @SerialName(value = "timestamp")
    val timeStamp: Long
)
