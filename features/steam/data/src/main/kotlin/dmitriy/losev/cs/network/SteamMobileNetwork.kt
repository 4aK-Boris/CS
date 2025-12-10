package dmitriy.losev.cs.network

import dmitriy.losev.cs.dso.ConfirmationDSO
import dmitriy.losev.cs.dso.ConfirmationsResponseDSO
import dmitriy.losev.cs.dso.auth.openid.GetRequestOpenIdParamsDSO
import dmitriy.losev.cs.dso.auth.openid.PostRequestOpenIdParamsDSO
import dmitriy.losev.cs.dso.TradeOfferAcceptResultDSO
import dmitriy.losev.cs.dso.TradeOfferInfoDSO

internal interface SteamMobileNetwork {

    suspend fun getConfirmations(steamId: Long, deviceId: String, confirmationKey: String): ConfirmationsResponseDSO

    suspend fun confirmTrade(steamId: Long, deviceId: String, confirmationKey: String, confirmation: ConfirmationDSO): Boolean

    suspend fun loginInOtherService(steamId: Long, openIdGetConfig: GetRequestOpenIdParamsDSO)

    suspend fun loginInOtherService(steamId: Long, openIdPostConfig: PostRequestOpenIdParamsDSO)

    suspend fun getTradeOffers(steamId: Long): String

    suspend fun acceptTradeOffer(steamId: Long, sessionId: String, tradeOfferInfo: TradeOfferInfoDSO): TradeOfferAcceptResultDSO
}
