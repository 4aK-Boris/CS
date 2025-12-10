package dmitriy.losev.cs.dso.auth


data class PollAuthSessionStatusRequestDSO(
    val steamId: Long,
    val clientId: String,
    val requestId: String,
    val interval: String
)
