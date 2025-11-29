package dmitriy.losev.cs.network

import dmitriy.losev.cs.dso.ItemsFloatDSO
import dmitriy.losev.cs.dso.ItemFloatDSO
import dmitriy.losev.cs.dso.ItemInfoFloatDSO
import dmitriy.losev.cs.dso.LinksDSO
import dmitriy.losev.cs.dso.StatisticsDSO

interface FloatNetwork {

    suspend fun getItemFloat(link: String): ItemInfoFloatDSO

    suspend fun getItemsFloat(links: LinksDSO): ItemsFloatDSO

    suspend fun getStatistics(): StatisticsDSO
}