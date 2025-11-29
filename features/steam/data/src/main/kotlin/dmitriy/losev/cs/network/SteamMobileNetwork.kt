package dmitriy.losev.cs.network

import dmitriy.losev.cs.dso.ConfirmationDSO
import dmitriy.losev.cs.dso.ConfirmationsResponseDSO
import dmitriy.losev.cs.dso.OpenIdGetConfigDSO
import dmitriy.losev.cs.dso.OpenIdPostConfigDSO
import dmitriy.losev.cs.dso.RSAKeyDSO
import dmitriy.losev.cs.dso.TradeOfferAcceptResultDSO
import dmitriy.losev.cs.dso.TradeOfferInfoDSO

internal interface SteamMobileNetwork {

    suspend fun getConfirmations(steamId: ULong, deviceId: String, confirmationKey: String): ConfirmationsResponseDSO

    suspend fun confirmTrade(steamId: ULong, deviceId: String, confirmationKey: String, confirmation: ConfirmationDSO): Boolean

    suspend fun getRSAKey(steamId: ULong, login: String): RSAKeyDSO

    suspend fun login(steamId: ULong, login: String, encryptedPassword: String, steamGuardCode: String, rsaKeyTimeStamp: Long)

    suspend fun loginInOtherService(steamId: ULong, openIdGetConfig: OpenIdGetConfigDSO)

    suspend fun loginInOtherService(steamId: ULong, openIdPostConfig: OpenIdPostConfigDSO)

    suspend fun getTradeOffers(steamId: ULong): String

    suspend fun acceptTradeOffer(steamId: ULong, sessionId: String, tradeOfferInfo: TradeOfferInfoDSO): TradeOfferAcceptResultDSO
}