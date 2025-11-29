package dmitriy.losev.cs.mappers.item

import dmitriy.losev.cs.dso.item.ItemSaleOfferDSO
import dmitriy.losev.cs.dto.item.ItemSaleOfferDTO
import dmitriy.losev.cs.dto.ListingInfoDTO
import org.koin.core.annotation.Factory

@Factory
class ItemSaleOfferMapper {

    fun map(value: Triple<ULong, ULong, ListingInfoDTO>): ItemSaleOfferDSO {
        return ItemSaleOfferDSO(
            classId = value.first,
            instanceId = value.second,
            listingId = value.third.listingId,
            priceForSeller = value.third.priceForSeller,
            priceForBuyer = value.third.priceForBuyer,
            assetId = value.third.assetId,
            link = value.third.link
        )
    }

    fun map(value: ItemSaleOfferDSO): ItemSaleOfferDTO {
        return ItemSaleOfferDTO(
            classId = value.classId,
            instanceId = value.instanceId,
            listingId = value.listingId,
            priceForSeller = value.priceForSeller,
            priceForBuyer = value.priceForBuyer,
            assetId = value.assetId,
            link = value.link
        )
    }
}