package dmitriy.losev.cs.dto.skins

import java.time.LocalDateTime

data class SkinDTO(
    val id: Int,
    val name: String,
    val price: Double,
    val unlockAt: LocalDateTime?,
    val itemClassId: String,
    val createdAt: LocalDateTime,
    val itemFloat: Double,
    val nameTag: String?,
    val itemPaintIndex: Int?,
    val itemPaintSeed: Int?,
    val stickers: List<StickerDTO>
)