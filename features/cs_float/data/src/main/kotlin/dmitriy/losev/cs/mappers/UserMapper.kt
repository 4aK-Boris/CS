package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dso.UserDSO
import dmitriy.losev.cs.dto.UserDTO
import org.koin.core.annotation.Factory

@Factory
internal class UserMapper(
    private val firebaseMessagingMapper: FirebaseMessagingMapper,
    private val preferencesMapper: PreferencesMapper,
    private val statisticsMapper: StatisticsMapper,
    private val stripeConnectMapper: StripeConnectMapper
) {

    fun map(value: UserDSO): UserDTO {
        return UserDTO(
            avatar = value.avatar,
            away = value.away,
            balance = value.balance,
            email = value.email,
            fee = value.fee,
            firebaseMessaging = firebaseMessagingMapper.map(value = value.firebaseMessaging),
            flags = value.flags,
            has2fa = value.has2fa,
            hasApiKey = value.hasApiKey,
            knowYourCustomer = value.knowYourCustomer,
            notificationTopicOptOut = value.notificationTopicOptOut,
            obfuscatedId = value.obfuscatedId,
            online = value.online,
            pendingBalance = value.pendingBalance,
            preferences = preferencesMapper.map(value = value.preferences),
            stallPublic = value.stallPublic,
            statistics = statisticsMapper.map(value = value.statistics),
            steamId = value.steamId.toLong(),
            stripeConnect = stripeConnectMapper.map(),
            subscriptions = value.subscriptions,
            tradeToken = value.tradeToken,
            withdrawFee = value.withdrawFee
        )
    }
}
