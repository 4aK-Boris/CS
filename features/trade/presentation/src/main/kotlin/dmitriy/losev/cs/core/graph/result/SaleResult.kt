package dmitriy.losev.cs.core.graph.result

import dmitriy.losev.cs.core.graph.context.ItemIdentifier
import dmitriy.losev.cs.core.market.Market

/**
 * Результат продажи предмета
 */
data class SaleResult(
    val saleId: String,

    /**
     * СТАБИЛЬНЫЙ идентификатор проданного предмета
     * Используется для сопоставления продажи с предметом в пуле
     */
    val itemUniqueId: ItemIdentifier,

    /**
     * assetId на момент продажи (может измениться после трейда)
     * Сохраняется для отладки и логирования
     */
    val itemAssetId: String,

    val priceCents: Long,
    val market: Market,
    val saleTimestamp: Long = System.currentTimeMillis()
)