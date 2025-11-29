package dmitriy.losev.cs.network

import dmitriy.losev.cs.dso.item.RequestItemInfoDSO
import dmitriy.losev.cs.dso.item.ResponseItemInfoDSO
import dmitriy.losev.cs.dso.offer.RequestSaleOffersDSO
import dmitriy.losev.cs.dso.offer.ResponseSaleOffersDSO

interface CSMarketNetwork {

    suspend fun getItemInfo(requestItemInfo: RequestItemInfoDSO): ResponseItemInfoDSO

    suspend fun getSaleOffers(requestSaleOffers: RequestSaleOffersDSO): ResponseSaleOffersDSO
}