package dmitriy.losev.cs.usecases

import dmitriy.losev.cs.dto.StatisticsDTO
import kotlinx.coroutines.delay
import org.koin.core.annotation.Factory

@Factory
class AwaitIfQueenSizeIsBigUseCase(private val getStatisticsForCSFloatInspectorUseCase: GetStatisticsForCSFloatInspectorUseCase): BaseUseCase {

    suspend operator fun invoke(): Result<Unit> = runCatching {
        var statistics: StatisticsDTO
        do {
            statistics = getStatisticsForCSFloatInspectorUseCase.invoke().getOrThrow()
            delay(1000L)
        } while (statistics.queueSize > 0)
    }
}