package dmitriy.losev.cs.mappers.offers.response

import dmitriy.losev.cs.dso.offers.response.OffersResponseDSO
import dmitriy.losev.cs.dto.offers.response.OffersResponseDTO
import org.koin.core.annotation.Factory

@Factory
class OffersResponseMapper(private val offerItemMapper: OfferItemMapper) {

    fun map(value: OffersResponseDSO): OffersResponseDTO {
        return OffersResponseDTO().apply {
            addAll(value.map(transform = offerItemMapper::map))
        }
    }
}
