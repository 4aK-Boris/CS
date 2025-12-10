package dmitriy.losev.cs.dto

data class CsFloatAccountDTO(
    val actionableTrades: Int,
    val hasUnreadNotifications: Boolean,
    val pendingOffers: Int,
    val user: UserDTO
)
