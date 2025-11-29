package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.dto.item.RequestItemInfoDTO
import dmitriy.losev.cs.dto.item.ResponseItemInfoDTO
import dmitriy.losev.cs.dto.offer.RequestSaleOffersDTO
import dmitriy.losev.cs.dto.offer.ResponseSaleOffersDTO

interface MarketItemRepository {

    suspend fun getItemInfo(requestItemInfo: RequestItemInfoDTO): ResponseItemInfoDTO

    suspend fun getSaleOffers(requestSaleOffers: RequestSaleOffersDTO): ResponseSaleOffersDTO
}