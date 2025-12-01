package dmitriy.losev.cs.repositories

import org.koin.core.annotation.Factory

@Factory(binds = [DatabaseCharmRepository::class])
class DatabaseCharmRepositoryImpl
//    @Provided private val charmHandler: CharmHandler,
//    private val charmInfoMapper: CharmInfoMapper,
//    private val charmSaleOfferMapper: CharmSaleOfferMapper,
//    private val charmLinkMapper: CharmLinkMapper,
//    private val charmFloatMapper: CharmFloatMapper
: DatabaseCharmRepository {
    
//    override suspend fun insertCharmInfo(charmInfo: CharmInfoDTO): String? {
//        return charmHandler.insertCharmInfo(charmInfo = charmInfoMapper.map(value = charmInfo))
//    }
//
//    override suspend fun getCharmsInfo(delay: Long, count: Int): List<CharmInfoDTO> {
//        return charmHandler.getCharmsInfo(delay, count).map(transform = charmInfoMapper::map)
//    }
//
//    override suspend fun checkCharmSaleOffers(charms: List<Triple<ULong, ULong, ListingInfoDTO>>): List<CharmSaleOfferDTO> {
//        return charmHandler.checkCharmSaleOffers(charmSaleOffers = charms.map(transform = charmSaleOfferMapper::map)).map(transform = charmSaleOfferMapper::map)
//    }
//
//    override suspend fun insertCharmSaleOffers(charmSaleOffers: List<CharmSaleOfferDTO>): List<CharmLinkDTO> {
//        val charmSaleOffersDSO = charmSaleOffers.map(transform = charmSaleOfferMapper::map)
//        return charmHandler.insertCharmSaleOffers(charmSaleOffers = charmSaleOffersDSO).map(transform = charmLinkMapper::map)
//    }
//
//    override suspend fun insertCharmFloat(charmFloat: CharmFloatDTO): CharmFloatDTO {
//        charmHandler.insertCharmFloat(charmFloat = charmFloatMapper.map(value = charmFloat))
//        return charmFloat
//    }
//
//    override suspend fun insertCharmsFloat(charmsFloat: List<CharmFloatDTO>) {
//        charmHandler.insertCharmsFloat(charmsFloat = charmsFloat.map(transform = charmFloatMapper::map))
//    }
}
