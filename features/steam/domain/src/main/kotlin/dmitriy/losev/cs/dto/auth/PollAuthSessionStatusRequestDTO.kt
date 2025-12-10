package dmitriy.losev.cs.dto.auth


data class PollAuthSessionStatusRequestDTO(
    val steamId: Long,
    val clientId: String,
    val requestId: String,
    val interval: Int
)
