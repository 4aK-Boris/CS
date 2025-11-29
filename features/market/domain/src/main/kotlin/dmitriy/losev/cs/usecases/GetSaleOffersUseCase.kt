package dmitriy.losev.cs.usecases

import dmitriy.losev.cs.dto.offer.RequestSaleOffersDTO
import dmitriy.losev.cs.dto.offer.ResponseSaleOffersDTO
import dmitriy.losev.cs.repositories.MarketItemRepository
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetSaleOffersUseCase(@Provided private val marketItemRepository: MarketItemRepository): BaseUseCase {

    suspend operator fun invoke(requestSaleOffers: RequestSaleOffersDTO): Result<ResponseSaleOffersDTO> = runCatching {
        marketItemRepository.getSaleOffers(requestSaleOffers)
    }
}