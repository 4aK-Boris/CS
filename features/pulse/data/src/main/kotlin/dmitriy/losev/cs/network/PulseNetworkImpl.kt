package dmitriy.losev.cs.network

import dmitriy.losev.cs.clients.PulseNetworkClient
import dmitriy.losev.cs.dso.auth.AuthRequestDSO
import dmitriy.losev.cs.dso.auth.AuthResponseDSO
import dmitriy.losev.cs.dso.offers.request.OffersRequestDSO
import dmitriy.losev.cs.dso.offers.response.OffersResponseDSO
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory(binds = [PulseNetwork::class])
internal class PulseNetworkImpl(@Provided private val pulseNetworkClient: PulseNetworkClient): PulseNetwork {

    override suspend fun getAuthToken(authRequest: AuthRequestDSO): AuthResponseDSO {
        return pulseNetworkClient.post(
            handle = AUTH_HANDLE,
            requestClazz = AuthRequestDSO::class,
            responseClazz = AuthResponseDSO::class,
            body = authRequest
        )
    }

    override suspend fun getOffers(offersRequest: OffersRequestDSO): OffersResponseDSO {
        return pulseNetworkClient.post(
            handle = OFFERS_HANDLE.format(offersRequest.firstMarketOptions.marketName, offersRequest.secondMarketOptions.marketName),
            headers = mapOf("Authorization" to "Bearer ${offersRequest.authToken}"),
            requestClazz = OffersRequestDSO::class,
            responseClazz = OffersResponseDSO::class,
            body = offersRequest
        )
    }

    companion object {
        private const val AUTH_HANDLE = "/table/user/authenticate"
        private const val OFFERS_HANDLE = "/table/counter-strike/%s/%s/all"
    }
}