package dmitriy.losev.cs.dto.offers.response

import java.time.LocalDateTime

data class MarketDTO(
    val id: Int,
    val price: Double,
    val realPrice: Double,
    val overriddenPrice: Double?,
    val realPriceCurrency: String,
    val bestOfferCount: Int,
    val totalOffersCount: Int,
    val historyUpdateTime: LocalDateTime,
    val offersUpdateTime: LocalDateTime,
    val overstockInfo: String?,
    val holdInfoResponse: HoldInfoResponseDTO?,
    val soldOutTime: Long?
)