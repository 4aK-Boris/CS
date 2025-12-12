package dmitriy.losev.cs.mappers.pulse

import dmitriy.losev.cs.dso.pulse.MarketItemDSO
import dmitriy.losev.cs.dto.pulse.MarketItemDTO
import org.koin.core.annotation.Factory
import kotlin.time.ExperimentalTime
import kotlin.time.toJavaInstant

@Factory
@OptIn(ExperimentalTime::class)
internal class MarketItemMapper {

    fun map(value: MarketItemDTO): MarketItemDSO {
        return MarketItemDSO(
            marketHashName = value.marketHashName,
            market = value.market.title,
            minPrice = value.minPrice,
            buyOrderPrice = value.buyOrderPrice,
            tradeOnPrice = value.tradeOnPrice,
            weeklySalesCount = value.weeklySalesCount,
            createdAt = value.createdAt.toJavaInstant()
        )
    }
}
