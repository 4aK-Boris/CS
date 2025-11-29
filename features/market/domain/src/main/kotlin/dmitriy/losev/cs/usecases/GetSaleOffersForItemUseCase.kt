package dmitriy.losev.cs.usecases

import dmitriy.losev.cs.dto.ListingInfoDTO
import dmitriy.losev.cs.dto.offer.RequestSaleOffersDTO
import dmitriy.losev.cs.dto.offer.ResponseSaleOffersDTO
import org.koin.core.annotation.Factory
import kotlin.mapCatching

@Factory
class GetSaleOffersForItemUseCase(private val getSaleOffersUseCase: GetSaleOffersUseCase): BaseUseCase {

    suspend operator fun invoke(itemName: String, startPosition: Int, requestCount: Int = 100): Result<List<ListingInfoDTO>>  {
        val request = RequestSaleOffersDTO(itemName = itemName, start = startPosition, count = requestCount)
        return getSaleOffersUseCase.invoke(request).mapCatching(transform = ResponseSaleOffersDTO::listingInfo)
    }
}