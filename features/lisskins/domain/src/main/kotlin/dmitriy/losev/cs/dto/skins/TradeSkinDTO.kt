package dmitriy.losev.cs.dto.skins

import java.time.LocalDateTime

data class TradeSkinDTO(
    val id: Int,
    val name: String,
    val price: Double,
    val status: String,
    val returnReason: String?,
    val returnChargedCommission: Double?,
    val error: String?,
    val steamTradeOfferId: String?,
    val steamTradeOfferCreatedAt: LocalDateTime?,
    val steamTradeOfferExpiryAt: LocalDateTime?,
    val steamTradeOfferFinishedAt: LocalDateTime?
)