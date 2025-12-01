package dmitriy.losev.cs.network

import dmitriy.losev.cs.EMPTY_STRING
import dmitriy.losev.cs.TimeHandler
import dmitriy.losev.cs.clients.SteamMobileProxyClient
import dmitriy.losev.cs.dso.ConfirmationDSO
import dmitriy.losev.cs.dso.ConfirmationsResponseDSO
import dmitriy.losev.cs.dso.OpenIdGetConfigDSO
import dmitriy.losev.cs.dso.OpenIdPostConfigDSO
import dmitriy.losev.cs.dso.RSAKeyDSO
import dmitriy.losev.cs.dso.TradeOfferAcceptResultDSO
import dmitriy.losev.cs.dso.TradeOfferInfoDSO
import java.net.URLEncoder
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Singleton

@Singleton(binds = [SteamMobileNetwork::class])
class SteamMobileNetworkImpl(
    @Provided private val timeHandler: TimeHandler,
    @Provided private val steamMobileProxyClient: SteamMobileProxyClient
) : SteamMobileNetwork {

    override suspend fun getConfirmations(steamId: ULong, deviceId: String, confirmationKey: String): ConfirmationsResponseDSO {
        return steamMobileProxyClient.get(
            steamId = steamId,
            handle = GET_CONFIRMATIONS_HANDLE,
            responseClazz = ConfirmationsResponseDSO::class,
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

    override suspend fun confirmTrade(steamId: ULong, deviceId: String, confirmationKey: String, confirmation: ConfirmationDSO): Boolean {
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

    override suspend fun getRSAKey(steamId: ULong, login: String): RSAKeyDSO {
        return steamMobileProxyClient.postWithUrlEncodedForm(
            steamId = steamId,
            handle = GET_RSA_KEY_HANDLE,
            responseClazz = RSAKeyDSO::class,
            formParams = mapOf("username" to login)
        )
    }

    override suspend fun login(steamId: ULong, login: String, encryptedPassword: String, steamGuardCode: String, rsaKeyTimeStamp: Long) {
        steamMobileProxyClient.postWithUrlEncodedForm(
            steamId = steamId,
            handle = LOGIN_HANDLE,
            responseClazz = String::class,
            formParams = mapOf(
                "username" to login,
                "password" to encryptedPassword.urlEncode(),
                "twofactorcode" to steamGuardCode,
                "emailauth" to EMPTY_STRING,
                "loginfriendlyname" to EMPTY_STRING,
                "captchagid" to "-1",
                "captcha_text" to EMPTY_STRING,
                "emailsteamid" to EMPTY_STRING,
                "rsatimestamp" to rsaKeyTimeStamp.toString(),
                "remember_login" to "true",
                "donotcache" to timeHandler.currentTime.toString(),
            )
        )
    }

    override suspend fun loginInOtherService(steamId: ULong, openIdGetConfig: OpenIdGetConfigDSO) {
        steamMobileProxyClient.getWithoutResponse(
            steamId = steamId,
            handle = LOGIN_IN_OTHER_SERVICE_HANDLE,
            params = openIdGetConfig.convertToParameters()
        )
    }

    override suspend fun loginInOtherService(steamId: ULong, openIdPostConfig: OpenIdPostConfigDSO) {
        steamMobileProxyClient.getWithoutResponse(
            steamId = steamId,
            handle = LOGIN_IN_OTHER_SERVICE_HANDLE,
            params = openIdPostConfig.convertToParameters()
        )
    }

    override suspend fun getTradeOffers(steamId: ULong): String {
        return steamMobileProxyClient.getWithTextBody(
            steamId = steamId,
            handle = GET_TRADE_OFFERS_HANDLE
        )
    }

    override suspend fun acceptTradeOffer(steamId: ULong, sessionId: String, tradeOfferInfo: TradeOfferInfoDSO): TradeOfferAcceptResultDSO {
        return steamMobileProxyClient.postWithUrlEncodedForm(
            steamId = steamId,
            handle = ACCEPT_TRADE_OFFER_HANDLE,
            responseClazz = TradeOfferAcceptResultDSO::class,
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

    private fun String.urlEncode(): String = URLEncoder.encode(this, "UTF-8")

    companion object {

        private const val GET_CONFIRMATIONS_HANDLE = "/mobileconf/getlist"
        private const val CONFIRM_TRADE_HANDLE = "/mobileconf/ajaxop"
        private const val GET_RSA_KEY_HANDLE = "/login/getrsakey/"
        private const val LOGIN_HANDLE = "/login/dologin/"
        private const val LOGIN_IN_OTHER_SERVICE_HANDLE = "/openid/login"
        private const val GET_TRADE_OFFERS_HANDLE = "/my/tradeoffers/"
        private const val ACCEPT_TRADE_OFFER_HANDLE = "/tradeoffer/%s/accept"
    }
}
