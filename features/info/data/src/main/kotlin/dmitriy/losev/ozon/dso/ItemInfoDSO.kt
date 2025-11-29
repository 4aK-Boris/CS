package dmitriy.losev.ozon.dso

/**
 * Полная информация о предмете CS2
 */
data class ItemInfoDSO(
    // Основная информация
    val accountId: Long = 0,
    val itemId: ULong = 0UL,
    val defIndex: Int = 0,
    val paintIndex: Int = 0,
    val rarity: Int = 0,
    val quality: Int = 0,

    // Износ и паттерн
    val floatValue: Float = 0f,  // paintwear, конвертированный в float
    val paintSeed: Int = 0,      // patternIndex

    // StatTrak / Souvenir
    val killEaterScoreType: Int = 0,
    val killEaterValue: Int = 0,

    // Кастомизация
    val customName: String = "",
    val stickers: List<StickerDSO> = emptyList(),
    val keychains: List<StickerDSO> = emptyList(),  // Брелки
    val variations: List<StickerDSO> = emptyList(),

    // Дополнительная информация
    val inventory: Int = 0,
    val origin: Int = 0,
    val questId: Int = 0,
    val dropReason: Int = 0,
    val musicIndex: Int = 0,
    val entIndex: Int = 0,
    val petIndex: Int = 0,
    val style: Int = 0,
    val upgradeLevel: Int = 0
)
