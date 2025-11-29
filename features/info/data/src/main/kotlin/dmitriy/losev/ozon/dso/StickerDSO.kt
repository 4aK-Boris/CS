package dmitriy.losev.ozon.dso

/**
 * Информация о стикере/брелке/вариации
 */
data class StickerDSO(
    val slot: Int = 0,
    val stickerId: Int = 0,
    val wear: Float = 0f,
    val scale: Float = 0f,
    val rotation: Float = 0f,
    val tintId: Int = 0,
    val offsetX: Float = 0f,
    val offsetY: Float = 0f,
    val offsetZ: Float = 0f,
    val pattern: Int = 0,
    val highlightReel: Int = 0
)