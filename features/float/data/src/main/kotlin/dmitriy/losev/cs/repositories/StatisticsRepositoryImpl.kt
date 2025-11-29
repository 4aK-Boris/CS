package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.dto.StatisticsDTO
import dmitriy.losev.cs.mappers.StatisticsMapper
import dmitriy.losev.cs.network.FloatNetwork
import org.koin.core.annotation.Factory

@Factory(binds = [StatisticsRepository::class])
class StatisticsRepositoryImpl(
    private val floatNetwork: FloatNetwork,
    private val statisticsMapper: StatisticsMapper
): StatisticsRepository {

    override suspend fun getStatistics(): StatisticsDTO {
        return statisticsMapper.map(value = floatNetwork.getStatistics())
    }
}