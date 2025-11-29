package dmitriy.losev.cs.dso.steam


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SteamAccountDSO(

    @SerialName(value = "account_name")
    val accountName: String,

    @SerialName(value = "device_id")
    val deviceId: String,

    @SerialName(value = "fully_enrolled")
    val fullyEnrolled: Boolean,

    @SerialName(value = "identity_secret")
    val identitySecret: String,

    @SerialName(value = "revocation_code")
    val revocationCode: String,

    @SerialName(value = "secret_1")
    val secret: String,

    @SerialName(value = "serial_number")
    val serialNumber: String,

    @SerialName(value = "server_time")
    val serverTime: Long,

    @SerialName(value = "Session")
    val session: Session,

    @SerialName(value = "shared_secret")
    val sharedSecret: String,

    @SerialName(value = "status")
    val status: Int,

    @SerialName(value = "token_gid")
    val tokenGid: String,

    @SerialName(value = "uri")
    val uri: String
)