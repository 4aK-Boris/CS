package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.dto.item.RequestItemInfoDTO
import dmitriy.losev.cs.dto.item.ResponseItemInfoDTO
import dmitriy.losev.cs.dto.offer.RequestSaleOffersDTO
import dmitriy.losev.cs.dto.offer.ResponseSaleOffersDTO
import dmitriy.losev.cs.mappers.item.RequestItemInfoMapper
import dmitriy.losev.cs.mappers.item.ResponseItemInfoMapper
import dmitriy.losev.cs.mappers.offer.RequestSaleOffersMapper
import dmitriy.losev.cs.mappers.offer.ResponseSaleOffersMapper
import dmitriy.losev.cs.network.CSMarketNetwork
import org.koin.core.annotation.Factory

@Factory(binds = [MarketItemRepository::class])
class MarketItemRepositoryImpl(
    private val csMarketNetwork: CSMarketNetwork,
    private val requestItemInfoMapper: RequestItemInfoMapper,
    private val responseItemInfoMapper: ResponseItemInfoMapper,
    private val requestSaleOffersMapper: RequestSaleOffersMapper,
    private val responseSaleOffersMapper: ResponseSaleOffersMapper
): MarketItemRepository {

    override suspend fun getItemInfo(requestItemInfo: RequestItemInfoDTO): ResponseItemInfoDTO {
        return responseItemInfoMapper.map(value = csMarketNetwork.getItemInfo(requestItemInfo = requestItemInfoMapper.map(value = requestItemInfo)))
    }

    override suspend fun getSaleOffers(requestSaleOffers: RequestSaleOffersDTO): ResponseSaleOffersDTO {
        return responseSaleOffersMapper.map(value = csMarketNetwork.getSaleOffers(requestSaleOffers = requestSaleOffersMapper.map(value = requestSaleOffers)))
    }
}