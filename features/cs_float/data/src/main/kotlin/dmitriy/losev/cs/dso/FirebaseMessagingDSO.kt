package dmitriy.losev.cs.dso


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FirebaseMessagingDSO(

    @SerialName(value = "last_updated")
    val lastUpdated: String?,

    @SerialName(value = "platform")
    val platform: String?
)
