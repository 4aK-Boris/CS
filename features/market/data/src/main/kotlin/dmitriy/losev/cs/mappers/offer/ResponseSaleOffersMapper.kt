package dmitriy.losev.cs.mappers.offer

import dmitriy.losev.cs.dso.offer.ResponseSaleOffersDSO
import dmitriy.losev.cs.dto.offer.ResponseSaleOffersDTO
import org.koin.core.annotation.Factory

@Factory
class ResponseSaleOffersMapper(private val listingInfoMapper: ListingInfoMapper) {

    fun map(value: ResponseSaleOffersDSO): ResponseSaleOffersDTO {
        return ResponseSaleOffersDTO(
            success = value.success,
            start = value.start,
            pageSize = value.pageSize,
            totalCount = value.totalCount,
            listingInfo = value.listingInfo.values.map(transform = listingInfoMapper::map)
        )
    }
}