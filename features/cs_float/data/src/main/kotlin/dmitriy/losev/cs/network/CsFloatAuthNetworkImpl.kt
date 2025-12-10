package dmitriy.losev.cs.network

import dmitriy.losev.cs.clients.CsFloatProxyClient
import dmitriy.losev.cs.dso.CsFloatAccountDSO
import dmitriy.losev.cs.dso.OpenIdParamsDSO
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Singleton

@Singleton(binds = [CsFloatAuthNetwork::class])
internal class CsFloatAuthNetworkImpl(@Provided private val csFloatProxyClient: CsFloatProxyClient): CsFloatAuthNetwork {

    override suspend fun authWithOpenIdOnMainSite(openIdParams: OpenIdParamsDSO): String {
        return csFloatProxyClient.getWithTextBody(
            steamId = openIdParams.steamId,
            handle = AUTH_WITH_OPEN_ID_ON_MAIN_SITE_HANDLE,
            params = openIdParams.convertToParameters(),
            headers = mapOf(
                "Referer" to "https://steamcommunity.com/"
            )
        )
    }

    override suspend fun authWithOpenId(openIdParams: OpenIdParamsDSO): CsFloatAccountDSO {
        return csFloatProxyClient.get(
            steamId = openIdParams.steamId,
            handle = AUTH_WITH_OPEN_ID_HANDLE,
            params = openIdParams.convertToParameters()
        )
    }

    companion object {

        private const val AUTH_WITH_OPEN_ID_ON_MAIN_SITE_HANDLE = "/"
        private const val AUTH_WITH_OPEN_ID_HANDLE = "/api/v1/auth/login"
    }
}
