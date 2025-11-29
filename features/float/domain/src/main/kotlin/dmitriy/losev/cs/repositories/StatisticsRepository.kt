package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.dto.StatisticsDTO

interface StatisticsRepository {

    suspend fun getStatistics(): StatisticsDTO
}