package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.dto.auth.AuthRequestDTO
import dmitriy.losev.cs.dto.auth.AuthResponseDTO
import dmitriy.losev.cs.dto.offers.request.OffersRequestDTO
import dmitriy.losev.cs.dto.offers.response.OffersResponseDTO

interface PulseRepository {

    suspend fun getAuthToken(authRequest: AuthRequestDTO): AuthResponseDTO

    suspend fun getOffers(offersRequestDTO: OffersRequestDTO): OffersResponseDTO
}