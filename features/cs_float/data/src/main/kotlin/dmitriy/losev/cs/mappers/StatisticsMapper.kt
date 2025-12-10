package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dso.StatisticsDSO
import dmitriy.losev.cs.dto.StatisticsDTO
import org.koin.core.annotation.Factory

@Factory
internal class StatisticsMapper {

    fun map(value: StatisticsDSO): StatisticsDTO {
        return StatisticsDTO(
            medianTradeTime = value.medianTradeTime,
            totalAvoidedTrades = value.totalAvoidedTrades,
            totalFailedTrades = value.totalFailedTrades,
            totalPurchases = value.totalPurchases,
            totalSales = value.totalSales,
            totalTrades = value.totalTrades,
            totalVerifiedTrades = value.totalVerifiedTrades
        )
    }
}
