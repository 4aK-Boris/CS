package dmitriy.losev.cs.mappers.charm

import dmitriy.losev.cs.dso.charm.CharmSaleOfferDSO
import dmitriy.losev.cs.dto.ListingInfoDTO
import dmitriy.losev.cs.dto.charm.CharmSaleOfferDTO
import dmitriy.losev.cs.mappers.LongMapper
import org.koin.core.annotation.Factory

@Factory
class CharmSaleOfferMapper(private val longMapper: LongMapper) {

    fun map(value: Triple<ULong, ULong, ListingInfoDTO>): CharmSaleOfferDSO {
        return CharmSaleOfferDSO(
            classId = longMapper.map(value = value.first),
            instanceId = longMapper.map(value = value.second),
            listingId = longMapper.map(value = value.third.listingId),
            priceForSeller = value.third.priceForSeller,
            priceForBuyer = value.third.priceForBuyer,
            assetId = longMapper.map(value = value.third.assetId),
            link = value.third.link
        )
    }

    fun map(value: CharmSaleOfferDTO): CharmSaleOfferDSO {
        return CharmSaleOfferDSO(
            classId = longMapper.map(value = value.classId),
            instanceId = longMapper.map(value = value.instanceId),
            listingId = longMapper.map(value = value.listingId),
            priceForSeller = value.priceForSeller,
            priceForBuyer = value.priceForBuyer,
            assetId = longMapper.map(value = value.assetId),
            link = value.link
        )
    }

    fun map(value: CharmSaleOfferDSO): CharmSaleOfferDTO {
        return CharmSaleOfferDTO(
            classId = longMapper.map(value = value.classId),
            instanceId = longMapper.map(value = value.instanceId),
            listingId = longMapper.map(value = value.listingId),
            priceForSeller = value.priceForSeller,
            priceForBuyer = value.priceForBuyer,
            assetId = longMapper.map(value = value.assetId),
            link = value.link
        )
    }
}