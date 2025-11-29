package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.dto.auth.AuthRequestDTO
import dmitriy.losev.cs.dto.auth.AuthResponseDTO
import dmitriy.losev.cs.dto.offers.request.OffersRequestDTO
import dmitriy.losev.cs.dto.offers.response.OffersResponseDTO
import dmitriy.losev.cs.mappers.auth.AuthRequestMapper
import dmitriy.losev.cs.mappers.auth.AuthResponseMapper
import dmitriy.losev.cs.mappers.offers.request.OffersRequestMapper
import dmitriy.losev.cs.mappers.offers.response.OffersResponseMapper
import dmitriy.losev.cs.network.PulseNetwork
import org.koin.core.annotation.Factory

@Factory(binds = [PulseRepository::class])
class PulseRepositoryImpl(
    private val pulseNetwork: PulseNetwork,
    private val authRequestMapper: AuthRequestMapper,
    private val authResponseMapper: AuthResponseMapper,
    private val offersRequestMapper: OffersRequestMapper,
    private val offersResponseMapper: OffersResponseMapper
): PulseRepository {

    override suspend fun getAuthToken(authRequest: AuthRequestDTO): AuthResponseDTO {
        return authResponseMapper.map(value = pulseNetwork.getAuthToken(authRequest = authRequestMapper.map(value = authRequest)))
    }

    override suspend fun getOffers(offersRequestDTO: OffersRequestDTO): OffersResponseDTO {
        return offersResponseMapper.map(value = pulseNetwork.getOffers(offersRequest = offersRequestMapper.map(value = offersRequestDTO)))
    }
}