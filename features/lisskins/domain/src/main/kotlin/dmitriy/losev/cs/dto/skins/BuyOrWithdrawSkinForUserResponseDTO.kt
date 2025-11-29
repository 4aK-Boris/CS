package dmitriy.losev.cs.dto.skins

import java.time.LocalDateTime

data class BuyOrWithdrawSkinForUserResponseDTO(
    val purchaseId: Int,
    val steamId: String,
    val createdAt: LocalDateTime,
    val customId: Long?,
    val skins: List<TradeSkinDTO>,
)