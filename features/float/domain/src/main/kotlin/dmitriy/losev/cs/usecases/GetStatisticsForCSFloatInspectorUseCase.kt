package dmitriy.losev.cs.usecases

import dmitriy.losev.cs.dto.StatisticsDTO
import dmitriy.losev.cs.repositories.StatisticsRepository
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetStatisticsForCSFloatInspectorUseCase(@Provided private val statisticsRepository: StatisticsRepository): BaseUseCase {

    suspend operator fun invoke(): Result<StatisticsDTO> = runCatching {
        statisticsRepository.getStatistics()
    }
}