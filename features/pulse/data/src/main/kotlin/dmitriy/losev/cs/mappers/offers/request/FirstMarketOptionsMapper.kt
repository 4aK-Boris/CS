package dmitriy.losev.cs.mappers.offers.request

import dmitriy.losev.cs.dso.offers.request.FirstMarketOptionsDSO
import dmitriy.losev.cs.dto.offers.request.MarketOptionsDTO
import org.koin.core.annotation.Factory

@Factory
class FirstMarketOptionsMapper(
    private val marketCountFilterMapper: MarketCountFilterMapper,
    private val marketPriceFilterMapper: MarketPriceFilterMapper,
    private val updateTimeFilterMapper: UpdateTimeFilterMapper
) {

    fun map(value: MarketOptionsDTO): FirstMarketOptionsDSO {
        return FirstMarketOptionsDSO(
            marketName = value.market.title,
            marketCountFilter = marketCountFilterMapper.map(value = value.marketCountFilter),
            marketPriceFilter = marketPriceFilterMapper.map(value = value.marketPriceFilter),
            updateTimeFilter = updateTimeFilterMapper.map(value = value.updateTimeFilter),
            marketPriceType = value.marketPriceType.title
        )
    }
}
