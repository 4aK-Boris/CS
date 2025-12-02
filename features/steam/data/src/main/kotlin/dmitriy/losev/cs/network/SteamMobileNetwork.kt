package dmitriy.losev.cs.network

import dmitriy.losev.cs.dso.ConfirmationDSO
import dmitriy.losev.cs.dso.ConfirmationsResponseDSO
import dmitriy.losev.cs.dso.OpenIdGetConfigDSO
import dmitriy.losev.cs.dso.OpenIdPostConfigDSO
import dmitriy.losev.cs.dso.RSAKeyDSO
import dmitriy.losev.cs.dso.TradeOfferAcceptResultDSO
import dmitriy.losev.cs.dso.TradeOfferInfoDSO

internal interface SteamMobileNetwork {

    suspend fun getConfirmations(steamId: Long, deviceId: String, confirmationKey: String): ConfirmationsResponseDSO

    suspend fun confirmTrade(steamId: Long, deviceId: String, confirmationKey: String, confirmation: ConfirmationDSO): Boolean

    suspend fun getRSAKey(steamId: Long, login: String): RSAKeyDSO

    suspend fun login(steamId: Long, login: String, encryptedPassword: String, steamGuardCode: String, rsaKeyTimeStamp: Long)

    suspend fun loginInOtherService(steamId: Long, openIdGetConfig: OpenIdGetConfigDSO)

    suspend fun loginInOtherService(steamId: Long, openIdPostConfig: OpenIdPostConfigDSO)

    suspend fun getTradeOffers(steamId: Long): String

    suspend fun acceptTradeOffer(steamId: Long, sessionId: String, tradeOfferInfo: TradeOfferInfoDSO): TradeOfferAcceptResultDSO
}
