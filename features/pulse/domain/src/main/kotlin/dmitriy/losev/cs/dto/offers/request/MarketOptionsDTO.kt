package dmitriy.losev.cs.dto.offers.request

import dmitriy.losev.cs.core.PriceType
import dmitriy.losev.cs.pulse.Market

data class MarketOptionsDTO(
    val market: Market,
    val marketCountFilter: MarketCountFilterDTO = MarketCountFilterDTO(),
    val marketPriceFilter: MarketPriceFilterDTO = MarketPriceFilterDTO(),
    val updateTimeFilter: UpdateTimeFilterDTO = UpdateTimeFilterDTO(),
    val marketPriceType: PriceType,
)
