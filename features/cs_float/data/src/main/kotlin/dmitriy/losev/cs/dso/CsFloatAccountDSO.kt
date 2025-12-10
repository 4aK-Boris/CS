package dmitriy.losev.cs.dso


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CsFloatAccountDSO(

    @SerialName(value = "actionable_trades")
    val actionableTrades: Int,

    @SerialName(value = "has_unread_notifications")
    val hasUnreadNotifications: Boolean,

    @SerialName(value = "pending_offers")
    val pendingOffers: Int,

    @SerialName(value = "user")
    val user: UserDSO
)
