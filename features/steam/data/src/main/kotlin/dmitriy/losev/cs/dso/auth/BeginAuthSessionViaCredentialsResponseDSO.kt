package dmitriy.losev.cs.dso.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BeginAuthSessionViaCredentialsResponseDSO(

    @SerialName(value = "client_id")
    val clientId: String,

    @SerialName(value = "request_id")
    val requestId: String,

    @SerialName(value = "interval")
    val interval: Int,

    @SerialName(value = "allowed_confirmations")
    val allowedConfirmations: List<ConfirmationTypeDSO>,

    @SerialName(value = "steamid")
    val steamId: String,

    @SerialName(value = "weak_token")
    val weakToken: String,

    @SerialName(value = "extended_error_message")
    val extendedErrorMessage: String
)
