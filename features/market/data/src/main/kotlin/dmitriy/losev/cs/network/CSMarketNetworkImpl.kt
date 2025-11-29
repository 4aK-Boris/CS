package dmitriy.losev.cs.network

import dmitriy.losev.cs.clients.CSMarketProxyNetworkClient
import dmitriy.losev.cs.dso.item.RequestItemInfoDSO
import dmitriy.losev.cs.dso.item.ResponseItemInfoDSO
import dmitriy.losev.cs.dso.offer.RequestSaleOffersDSO
import dmitriy.losev.cs.dso.offer.ResponseSaleOffersDSO
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory(binds = [CSMarketNetwork::class])
class CSMarketNetworkImpl(@Provided private val csMarketProxyNetworkClient: CSMarketProxyNetworkClient) : CSMarketNetwork {

    override suspend fun getItemInfo(requestItemInfo: RequestItemInfoDSO): ResponseItemInfoDSO {
        return csMarketProxyNetworkClient.get(
            handle = ITEM_INFO_HANDLE,
            responseClazz = ResponseItemInfoDSO::class,
            params = requestItemInfo.toParams()
        )
    }

    override suspend fun getSaleOffers(requestSaleOffers: RequestSaleOffersDSO): ResponseSaleOffersDSO {
        return csMarketProxyNetworkClient.get(
            handle = SALE_OFFERS_HANDLE.format(requestSaleOffers.query),
            responseClazz = ResponseSaleOffersDSO::class,
            params = requestSaleOffers.toParams()
        )
    }

    companion object {
        private const val ITEM_INFO_HANDLE = "/search/render"
        private const val SALE_OFFERS_HANDLE = "/listings/730/%s/render"
    }
}