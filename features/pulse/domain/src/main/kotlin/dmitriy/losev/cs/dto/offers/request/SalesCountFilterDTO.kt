package dmitriy.losev.cs.dto.offers.request

import dmitriy.losev.cs.pulse.Market

data class SalesCountFilterDTO(
    val id: Int,
    val market: Market,
    val period: String,
    val salesCount: Int = 50
)
