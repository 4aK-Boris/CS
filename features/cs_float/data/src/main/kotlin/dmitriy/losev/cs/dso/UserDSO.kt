package dmitriy.losev.cs.dso


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDSO(

    @SerialName(value = "avatar")
    val avatar: String,

    @SerialName(value = "away")
    val away: Boolean,

    @SerialName(value = "balance")
    val balance: Int,

    @SerialName(value = "email")
    val email: String? = null,

    @SerialName(value = "fee")
    val fee: Double,

    @SerialName(value = "firebase_messaging")
    val firebaseMessaging: FirebaseMessagingDSO,

    @SerialName(value = "flags")
    val flags: Int,

    @SerialName(value = "has_2fa")
    val has2fa: Boolean,

    @SerialName(value = "has_api_key")
    val hasApiKey: Boolean,

    @SerialName(value = "know_your_customer")
    val knowYourCustomer: String,

    @SerialName(value = "notification_topic_opt_out")
    val notificationTopicOptOut: Int,

    @SerialName(value = "obfuscated_id")
    val obfuscatedId: String,

    @SerialName(value = "online")
    val online: Boolean,

    @SerialName(value = "pending_balance")
    val pendingBalance: Int,

    @SerialName(value = "preferences")
    val preferences: PreferencesDSO,

    @SerialName(value = "stall_public")
    val stallPublic: Boolean,

    @SerialName(value = "statistics")
    val statistics: StatisticsDSO,

    @SerialName(value = "steam_id")
    val steamId: String,

    @SerialName(value = "stripe_connect")
    val stripeConnect: StripeConnectDSO,

    @SerialName(value = "subscriptions")
    val subscriptions: List<String?>,

    @SerialName(value = "trade_token")
    val tradeToken: String?,

    @SerialName(value = "username")
    val username: String,

    @SerialName(value = "withdraw_fee")
    val withdrawFee: Double
)
