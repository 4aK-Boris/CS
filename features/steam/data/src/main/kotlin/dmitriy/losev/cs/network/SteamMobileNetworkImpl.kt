package dmitriy.losev.cs.network

import dmitriy.losev.cs.EMPTY_STRING
import dmitriy.losev.cs.TimeHandler
import dmitriy.losev.cs.clients.BaseProxyClient
import dmitriy.losev.cs.dso.ConfirmationDSO
import dmitriy.losev.cs.dso.ConfirmationsResponseDSO
import dmitriy.losev.cs.dso.TradeOfferAcceptResultDSO
import dmitriy.losev.cs.dso.TradeOfferInfoDSO
import dmitriy.losev.cs.dso.auth.openid.GetRequestOpenIdParamsDSO
import dmitriy.losev.cs.dso.auth.openid.PostRequestOpenIdParamsDSO
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Singleton

@Singleton(binds = [SteamMobileNetwork::class])
class SteamMobileNetworkImpl(
    @Provided private val timeHandler: TimeHandler,
    @Provided private val steamMobileProxyClient: BaseProxyClient
) : SteamMobileNetwork {

    override suspend fun getConfirmations(steamId: Long, deviceId: String, confirmationKey: String): ConfirmationsResponseDSO {
        return steamMobileProxyClient.get(
            steamId = steamId,
            handle = GET_CONFIRMATIONS_HANDLE,
            params = mapOf(
                "p" to deviceId,
                "a" to steamId.toString(),
                "k" to confirmationKey,
                "t" to timeHandler.currentTimeInSeconds.toString(),
                "m" to "react",
                "tag" to "conf"
            )
        )
    }

    override suspend fun confirmTrade(steamId: Long, deviceId: String, confirmationKey: String, confirmation: ConfirmationDSO): Boolean {
        return steamMobileProxyClient.getWithoutResponse(
            steamId = steamId,
            handle = CONFIRM_TRADE_HANDLE,
            params = mapOf(
                "op" to "allow",
                "p" to deviceId,
                "a" to steamId.toString(),
                "k" to confirmationKey,
                "t" to timeHandler.currentTimeInSeconds.toString(),
                "m" to "react",
                "tag" to "allow",
                "cid" to confirmation.id,
                "ck" to confirmation.key
            )
        )
    }

    override suspend fun loginInOtherService(steamId: Long, openIdGetConfig: GetRequestOpenIdParamsDSO) {
        steamMobileProxyClient.getWithoutResponse(
            steamId = steamId,
            handle = LOGIN_IN_OTHER_SERVICE_HANDLE,
            params = openIdGetConfig.convertToParameters()
        )
    }

    override suspend fun loginInOtherService(steamId: Long, openIdPostConfig: PostRequestOpenIdParamsDSO) {
        steamMobileProxyClient.getWithoutResponse(
            steamId = steamId,
            handle = LOGIN_IN_OTHER_SERVICE_HANDLE,
            params = emptyMap()
        )
    }

    override suspend fun getTradeOffers(steamId: Long): String {
        return steamMobileProxyClient.getWithTextBody(
            steamId = steamId,
            handle = GET_TRADE_OFFERS_HANDLE
        )
    }

    override suspend fun acceptTradeOffer(steamId: Long, sessionId: String, tradeOfferInfo: TradeOfferInfoDSO): TradeOfferAcceptResultDSO {
        return steamMobileProxyClient.postWithUrlEncodedForm(
            steamId = steamId,
            handle = ACCEPT_TRADE_OFFER_HANDLE,
            formParams = mapOf(
                "sessionid" to sessionId,
                "serverid" to "1",
                "tradeofferid" to tradeOfferInfo.offerId,
                "partner" to tradeOfferInfo.partnerId,
                "captcha" to EMPTY_STRING
            ),
            headers = mapOf("Referer" to "https://steamcommunity.com/tradeoffer/${tradeOfferInfo.offerId}/")
        )
    }

    companion object {

        private const val GET_CONFIRMATIONS_HANDLE = "/mobileconf/getlist"
        private const val CONFIRM_TRADE_HANDLE = "/mobileconf/ajaxop"
        private const val LOGIN_IN_OTHER_SERVICE_HANDLE = "/openid/login"
        private const val GET_TRADE_OFFERS_HANDLE = "/my/tradeoffers/"
        private const val ACCEPT_TRADE_OFFER_HANDLE = "/tradeoffer/%s/accept"
    }
}
