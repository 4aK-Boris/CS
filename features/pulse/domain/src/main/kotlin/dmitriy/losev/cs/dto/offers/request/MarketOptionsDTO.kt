package dmitriy.losev.cs.dto.offers.request

import dmitriy.losev.cs.pulse.Market
import dmitriy.losev.cs.pulse.MarketPriceType

data class MarketOptionsDTO(
    val market: Market,
    val marketCountFilter: MarketCountFilterDTO = MarketCountFilterDTO(),
    val marketPriceFilter: MarketPriceFilterDTO = MarketPriceFilterDTO(),
    val updateTimeFilter: UpdateTimeFilterDTO = UpdateTimeFilterDTO(),
    val marketPriceType: MarketPriceType,
)