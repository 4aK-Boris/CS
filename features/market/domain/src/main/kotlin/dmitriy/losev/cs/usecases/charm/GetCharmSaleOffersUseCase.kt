package dmitriy.losev.cs.usecases.charm

import dmitriy.losev.cs.dto.ListingInfoDTO
import dmitriy.losev.cs.dto.offer.RequestSaleOffersDTO
import dmitriy.losev.cs.dto.offer.ResponseSaleOffersDTO
import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.GetSaleOffersUseCase
import org.koin.core.annotation.Factory
import kotlin.mapCatching

@Factory
class GetCharmSaleOffersUseCase(private val getSaleOffersUseCase: GetSaleOffersUseCase): BaseUseCase {

    suspend operator fun invoke(name: String, startPosition: Int, requestCount: Int = 100): Result<List<ListingInfoDTO>>  {
        val request = RequestSaleOffersDTO(itemName = name, start = startPosition, count = requestCount)
        return getSaleOffersUseCase.invoke(request).mapCatching(transform = ResponseSaleOffersDTO::listingInfo)
    }
}