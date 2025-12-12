package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.dto.auth.AuthRequestDTO
import dmitriy.losev.cs.dto.auth.AuthResponseDTO
import dmitriy.losev.cs.dto.offers.request.OffersRequestDTO
import dmitriy.losev.cs.dto.offers.response.OffersResponseDTO
import dmitriy.losev.cs.pulse.Market
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
interface PulseRepository {

    suspend fun getAuthTokenFromNetwork(authRequest: AuthRequestDTO): AuthResponseDTO

    suspend fun getAuthTokenFromCache(): String?

    suspend fun setAuthTokenToCache(token: String): String

    suspend fun getOffers(offersRequestDTO: OffersRequestDTO): OffersResponseDTO

    suspend fun setMarketPriceUpdateAt(buyMarket: Market, sellMarket: Market)

    suspend fun getMarketPriceUpdateAt(buyMarket: Market, sellMarket: Market): Instant
}
