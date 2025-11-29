package dmitriy.losev.cs.network

import dmitriy.losev.cs.EMPTY_STRING
import dmitriy.losev.cs.clients.FloatNetworkClient
import dmitriy.losev.cs.dso.ItemsFloatDSO
import dmitriy.losev.cs.dso.ItemFloatDSO
import dmitriy.losev.cs.dso.ItemInfoFloatDSO
import dmitriy.losev.cs.dso.LinksDSO
import dmitriy.losev.cs.dso.StatisticsDSO
import org.koin.core.annotation.Factory

@Factory(binds = [FloatNetwork::class])
class FloatNetworkImpl(private val floatNetworkClient: FloatNetworkClient): FloatNetwork {

    override suspend fun getItemFloat(link: String): ItemInfoFloatDSO {
        return floatNetworkClient.get(
            handle = GET_ITEM_FLOAT_HANDLE,
            responseClazz = ItemInfoFloatDSO::class,
            params = mapOf(LINK_PARAMETER_KEY to link)
        )
    }

    override suspend fun getItemsFloat(links: LinksDSO): ItemsFloatDSO {
        return floatNetworkClient.post(
            handle = GET_ITEMS_FLOAT_HANDLE,
            requestClazz = LinksDSO::class,
            responseClazz = ItemsFloatDSO::class,
            body = links
        )
    }

    override suspend fun getStatistics(): StatisticsDSO {
        return floatNetworkClient.get(
            handle = GET_STATISTICS_HANDLE,
            responseClazz = StatisticsDSO::class
        )
    }

    companion object {

        private const val LINK_PARAMETER_KEY = "url"
        private const val GET_ITEM_FLOAT_HANDLE = EMPTY_STRING
        private const val GET_ITEMS_FLOAT_HANDLE = "/bulk"

        private const val GET_STATISTICS_HANDLE = "/stats"
    }
}