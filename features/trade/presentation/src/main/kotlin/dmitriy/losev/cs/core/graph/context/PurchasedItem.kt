package dmitriy.losev.cs.core.graph.context

import dmitriy.losev.cs.core.graph.market.StickerInfo
import dmitriy.losev.cs.core.market.Market
import java.time.Instant

/**
 * Предмет, купленный на маркете
 *
 * Важно: assetId меняется при трейде! Используйте uniqueId для отслеживания.
 * uniqueId стабилен и основан на D параметре (для скинов) или classId+instanceId (для кейсов)
 */
data class PurchasedItem(
    /**
     * СТАБИЛЬНЫЙ идентификатор предмета
     * Не меняется при трейдах!
     */
    val uniqueId: ItemIdentifier,

    /**
     * assetId - НЕСТАБИЛЬНЫЙ идентификатор
     * Меняется при трейде! Используется только для API запросов.
     */
    val assetId: String,

    val classId: String,
    val instanceId: String,
    val marketHashName: String,

    /**
     * Маркет, на котором был куплен предмет
     */
    val purchasedFrom: Market,

    /**
     * Цена покупки в копейках
     */
    val pricePaidCents: Long,

    /**
     * Inspect link для скинов (содержит D параметр)
     * Для кейсов, наклеек и т.д. - null
     */
    val inspectLink: String?,

    // Данные о скине
    val floatValue: Float?,
    val paintSeed: Int?,
    val paintIndex: Int?,
    val stickers: List<StickerInfo> = emptyList(),

    /**
     * Дата разблокировки трейда
     * null = можно торговать сразу
     */
    val tradeLockUntil: Instant?
) {
    /**
     * Проверяет, разблокирован ли предмет для трейда
     */
    fun isUnlocked(): Boolean {
        return tradeLockUntil == null || Instant.now().isAfter(tradeLockUntil)
    }

    /**
     * Проверяет, совпадает ли этот предмет с данным идентификатором
     * Используется для поиска предмета в инвентаре после покупки
     */
    fun matches(identifier: ItemIdentifier): Boolean {
        return uniqueId == identifier
    }
}