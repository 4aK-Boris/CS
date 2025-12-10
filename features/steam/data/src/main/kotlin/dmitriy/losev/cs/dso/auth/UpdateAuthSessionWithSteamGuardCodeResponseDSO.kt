package dmitriy.losev.cs.dso.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateAuthSessionWithSteamGuardCodeResponseDSO(

    @SerialName(value = "agreement_session_url")
    val agreementSessionUrl: String,
)
