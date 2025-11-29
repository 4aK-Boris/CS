package dmitriy.losev.cs.dso

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfirmationsResponseDSO(

    @SerialName(value = "success")
    val success: Boolean,

    @SerialName(value = "conf")
    val confirmations: List<ConfirmationDSO>? = null
)