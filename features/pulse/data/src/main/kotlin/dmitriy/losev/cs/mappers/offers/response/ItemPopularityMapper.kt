package dmitriy.losev.cs.mappers.offers.response

import dmitriy.losev.cs.dso.offers.response.ItemPopularityDSO
import dmitriy.losev.cs.dto.offers.response.ItemPopularityDTO
import dmitriy.losev.cs.pulse.Market
import org.koin.core.annotation.Factory

@Factory
class ItemPopularityMapper {

    fun map(value: ItemPopularityDSO): ItemPopularityDTO {
        return ItemPopularityDTO(
            market = Market.findMarketByTitle(title = value.market),
            salesCount = value.salesCount,
            marketItemUrl = value.marketItemUrl
        )
    }
}
