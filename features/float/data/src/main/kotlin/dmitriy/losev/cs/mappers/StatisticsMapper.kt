package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dso.StatisticsDSO
import dmitriy.losev.cs.dto.StatisticsDTO
import org.koin.core.annotation.Factory

@Factory
class StatisticsMapper {

    fun map(value: StatisticsDSO): StatisticsDTO {
        return StatisticsDTO(
            botsOnline = value.botsOnline,
            botsTotal = value.botsTotal,
            queueSize = value.queueSize,
            queueConcurrency = value.queueConcurrency
        )
    }
}