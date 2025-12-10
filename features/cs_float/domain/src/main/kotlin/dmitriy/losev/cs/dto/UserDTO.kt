package dmitriy.losev.cs.dto

data class UserDTO(
    val avatar: String,
    val away: Boolean,
    val balance: Int,
    val email: String?,
    val fee: Double,
    val firebaseMessaging: FirebaseMessagingDTO,
    val flags: Int,
    val has2fa: Boolean,
    val hasApiKey: Boolean,
    val knowYourCustomer: String,
    val notificationTopicOptOut: Int,
    val obfuscatedId: String,
    val online: Boolean,
    val pendingBalance: Int,
    val preferences: PreferencesDTO,
    val stallPublic: Boolean,
    val statistics: StatisticsDTO,
    val steamId: Long,
    val stripeConnect: StripeConnectDTO,
    val subscriptions: List<String?>,
    val tradeToken: String?,
    val withdrawFee: Double
)
