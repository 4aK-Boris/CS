package dmitriy.losev.cs.network

import dmitriy.losev.cs.dso.auth.AuthRequestDSO
import dmitriy.losev.cs.dso.auth.AuthResponseDSO
import dmitriy.losev.cs.dso.offers.request.OffersRequestDSO
import dmitriy.losev.cs.dso.offers.response.OffersResponseDSO

interface PulseNetwork {

    suspend fun getAuthToken(authRequest: AuthRequestDSO): AuthResponseDSO

    suspend fun getOffers(offersRequest: OffersRequestDSO): OffersResponseDSO
}