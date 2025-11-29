package dmitriy.losev.cs.dto.offers.response

import dmitriy.losev.cs.pulse.Market

data class ItemPopularityDTO(
    val market: Market,
    val salesCount: Int,
    val marketItemUrl: String?
)