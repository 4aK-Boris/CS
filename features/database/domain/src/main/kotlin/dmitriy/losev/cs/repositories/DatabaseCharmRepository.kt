package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.dto.ListingInfoDTO
import dmitriy.losev.cs.dto.charm.CharmFloatDTO
import dmitriy.losev.cs.dto.charm.CharmInfoDTO
import dmitriy.losev.cs.dto.charm.CharmLinkDTO
import dmitriy.losev.cs.dto.charm.CharmSaleOfferDTO

interface DatabaseCharmRepository {

    suspend fun insertCharmInfo(charmInfo: CharmInfoDTO): String?

    suspend fun getCharmsInfo(delay: Long, count: Int): List<CharmInfoDTO>

    suspend fun checkCharmSaleOffers(charms: List<Triple<ULong, ULong, ListingInfoDTO>>): List<CharmSaleOfferDTO>

    suspend fun insertCharmSaleOffers(charmSaleOffers: List<CharmSaleOfferDTO>): List<CharmLinkDTO>

    suspend fun insertCharmFloat(charmFloat: CharmFloatDTO): CharmFloatDTO

    suspend fun insertCharmsFloat(charmsFloat: List<CharmFloatDTO>)
}