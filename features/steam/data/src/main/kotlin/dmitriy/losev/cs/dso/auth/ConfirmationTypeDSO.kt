package dmitriy.losev.cs.dso.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfirmationTypeDSO(

    @SerialName(value = "confirmation_type")
    val confirmationType: Int
)
