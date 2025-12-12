package dmitriy.losev.cs.usecases.offers

import dmitriy.losev.cs.dto.offers.request.OffersRequestDTO
import dmitriy.losev.cs.dto.offers.response.OffersResponseDTO
import dmitriy.losev.cs.repositories.PulseRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetOffersUseCase(@Provided private val pulseRepository: PulseRepository): BaseUseCase {

    suspend operator fun invoke(offersRequest: OffersRequestDTO): Result<OffersResponseDTO> = runCatching {
        pulseRepository.getOffers(offersRequest)
    }
}
