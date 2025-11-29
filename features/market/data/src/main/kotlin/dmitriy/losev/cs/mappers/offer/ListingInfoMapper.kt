package dmitriy.losev.cs.mappers.offer

import dmitriy.losev.cs.dso.offer.ListingInfoDSO
import dmitriy.losev.cs.dto.ListingInfoDTO
import org.koin.core.annotation.Factory

@Factory
class ListingInfoMapper {

    fun map(value: ListingInfoDSO): ListingInfoDTO {
        val listingId = value.listingId.toULong()
        val assetId = value.asset.id.toULong()
        val link = value.asset.marketActions.first().link
            .replace(oldValue = "%listingid%", newValue = listingId.toString())
            .replace(oldValue = "%assetid%", newValue = assetId.toString())
        return ListingInfoDTO(
            listingId = listingId,
            priceForSeller = value.convertedPrice,
            priceForBuyer = value.convertedPrice + value.convertedFee,
            assetId = assetId,
            link = link,
        )
    }
}