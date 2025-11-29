package dmitriy.losev.cs.mappers.offer

import dmitriy.losev.cs.dso.offer.RequestSaleOffersDSO
import dmitriy.losev.cs.dto.offer.RequestSaleOffersDTO
import org.koin.core.annotation.Factory

@Factory
class RequestSaleOffersMapper {

    fun map(value: RequestSaleOffersDTO): RequestSaleOffersDSO {
        return RequestSaleOffersDSO(
            query = value.itemName,
            start = value.start.toString(),
            count = value.count.toString(),
        )
    }
}