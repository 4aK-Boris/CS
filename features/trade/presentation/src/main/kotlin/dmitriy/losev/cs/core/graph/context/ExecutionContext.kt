package dmitriy.losev.cs.core.graph.context

import java.util.concurrent.ConcurrentHashMap

class ExecutionContext(
    val steamId: ULong,
    private val results: MutableMap<String, NodeResult> = mutableMapOf(),
    private val itemPools: MutableMap<String, ItemPool> = ConcurrentHashMap()
) {
    fun setResult(nodeId: String, result: NodeResult) {
        results[nodeId] = result
    }

    fun getResult(nodeId: String): NodeResult? = results[nodeId]

    fun getItem(nodeId: String): PurchasedItem? =
        (results[nodeId] as? NodeResult.BuySuccess)?.item

    // ========== Работа с пулами предметов ==========

    /**
     * Создать новый пул предметов
     */
    fun createPool(poolId: String): ItemPool {
        val pool = ItemPool(poolId, steamId)
        itemPools[poolId] = pool
        return pool
    }

    /**
     * Получить пул по ID
     */
    fun getPool(poolId: String): ItemPool? = itemPools[poolId]

    /**
     * Получить пул или создать, если не существует
     */
    fun getOrCreatePool(poolId: String): ItemPool {
        return itemPools.getOrPut(poolId) { ItemPool(poolId, steamId) }
    }

    /**
     * Получить все пулы
     */
    fun getAllPools(): Map<String, ItemPool> = itemPools.toMap()

    /**
     * Удалить пул
     */
    fun removePool(poolId: String): ItemPool? = itemPools.remove(poolId)

    /**
     * Проверить существование пула
     */
    fun hasPool(poolId: String): Boolean = itemPools.containsKey(poolId)

    /**
     * Получить общее количество предметов во всех пулах
     */
    fun getTotalItemsCount(): Int = itemPools.values.sumOf { it.size() }

    /**
     * Получить общую стоимость всех предметов во всех пулах
     */
    fun getTotalValue(): Long = itemPools.values.sumOf { it.getTotalValue() }

    /**
     * Получить все предметы из пула в определенном состоянии
     */
    fun getItemsFromPoolByState(poolId: String, state: ItemState): List<PoolItem> {
        return getPool(poolId)?.getItemsByState(state) ?: emptyList()
    }

    /**
     * Добавить предмет в пул
     */
    fun addItemToPool(poolId: String, item: PurchasedItem, state: ItemState = ItemState.PURCHASED) {
        getOrCreatePool(poolId).addItem(item, state)
    }

    /**
     * Добавить несколько предметов в пул
     */
    fun addItemsToPool(poolId: String, items: List<PurchasedItem>, state: ItemState = ItemState.PURCHASED) {
        getOrCreatePool(poolId).addItems(items, state)
    }

    /**
     * Получить статистику по всем пулам
     */
    fun getAllPoolsStatistics(): Map<String, PoolStatistics> {
        return itemPools.mapValues { (_, pool) -> pool.getStatistics() }
    }

    /**
     * Вывести отчет по всем пулам
     */
    fun printPoolsReport(): String {
        return buildString {
            appendLine("=== Pools Report for Account: $steamId ===")
            appendLine("Total pools: ${itemPools.size}")
            appendLine("Total items: $getTotalItemsCount")
            appendLine("Total value: ${getTotalValue() / 100.0} руб")
            appendLine()
            itemPools.values.forEach { pool ->
                appendLine(pool.getStatistics().printSummary())
                appendLine()
            }
        }
    }
}