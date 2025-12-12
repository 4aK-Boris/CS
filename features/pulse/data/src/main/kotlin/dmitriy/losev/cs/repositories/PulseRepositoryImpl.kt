package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.core.MarketPriceUpdater
import dmitriy.losev.cs.core.TokenHolder
import dmitriy.losev.cs.dto.auth.AuthRequestDTO
import dmitriy.losev.cs.dto.auth.AuthResponseDTO
import dmitriy.losev.cs.dto.offers.request.OffersRequestDTO
import dmitriy.losev.cs.dto.offers.response.OffersResponseDTO
import dmitriy.losev.cs.mappers.auth.AuthRequestMapper
import dmitriy.losev.cs.mappers.auth.AuthResponseMapper
import dmitriy.losev.cs.mappers.offers.request.OffersRequestMapper
import dmitriy.losev.cs.mappers.offers.response.OffersResponseMapper
import dmitriy.losev.cs.network.PulseNetwork
import dmitriy.losev.cs.pulse.Market
import org.koin.core.annotation.Factory
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.time.toKotlinInstant

@OptIn(ExperimentalTime::class)
@Factory(binds = [PulseRepository::class])
internal class PulseRepositoryImpl(
    private val pulseNetwork: PulseNetwork,
    private val tokenHolder: TokenHolder,
    private val marketPriceUpdater: MarketPriceUpdater,
    private val authRequestMapper: AuthRequestMapper,
    private val authResponseMapper: AuthResponseMapper,
    private val offersRequestMapper: OffersRequestMapper,
    private val offersResponseMapper: OffersResponseMapper
): PulseRepository {

    override suspend fun getAuthTokenFromNetwork(authRequest: AuthRequestDTO): AuthResponseDTO {
        return authResponseMapper.map(value = pulseNetwork.getAuthToken(authRequest = authRequestMapper.map(value = authRequest)))
    }

    override suspend fun getAuthTokenFromCache(): String? {
        return tokenHolder.token
    }

    override suspend fun setAuthTokenToCache(token: String): String {
        return tokenHolder.update(token)
    }

    override suspend fun getOffers(offersRequestDTO: OffersRequestDTO): OffersResponseDTO {
        return offersResponseMapper.map(value = pulseNetwork.getOffers(offersRequest = offersRequestMapper.map(value = offersRequestDTO)))
    }

    override suspend fun setMarketPriceUpdateAt(buyMarket: Market, sellMarket: Market) {
        marketPriceUpdater.setMarketPriceUpdateAt(buyMarket, sellMarket)
    }

    override suspend fun getMarketPriceUpdateAt(buyMarket: Market, sellMarket: Market): Instant {
        return marketPriceUpdater.getMarketPriceUpdateAt(buyMarket, sellMarket).toKotlinInstant()
    }
}
