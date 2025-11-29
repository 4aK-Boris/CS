package dmitriy.losev.cs.dto.offers.request

import dmitriy.losev.cs.pulse.Market
import dmitriy.losev.cs.pulse.Period

data class SalesCountFilterDTO(
    val id: Int,
    val market: Market,
    val period: Period = Period.WEEK,
    val salesCount: Int = 50
)