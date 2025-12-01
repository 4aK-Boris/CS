package dmitriy.losev.cs.dso

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfirmationDSO(

    @SerialName(value = "id")
    val id: String,

    @SerialName(value = "nonce")
    val key: String,

    @SerialName(value = "type")
    val type: Int,

    @SerialName(value = "creator_id")
    val creatorId: String? = null
)
