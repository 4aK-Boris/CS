package dmitriy.losev.cs.core.graph.strategy

import dmitriy.losev.cs.core.market.Market

sealed interface ItemSelector {
    /**
     * Статические селекторы - для фиксированных предметов
     */
    data class ByName(val marketHashName: String) : ItemSelector
    data class ByNameAndFloat(val marketHashName: String, val maxFloat: Float, val minFloat: Float = 0f) : ItemSelector
    data class ByAssetId(val assetId: String) : ItemSelector
    data class FromPreviousNode(val nodeId: String) : ItemSelector

    /**
     * Динамические селекторы - для выбора предметов во время выполнения
     */

    /**
     * Получить предметы из Pulse API на основе критериев
     * @param filters фильтры для Pulse API (например, минимальная прибыль, тип скина и т.д.)
     * @param maxBudgetCents максимальный бюджет на закупку (в копейках)
     * @param maxItemsCount максимальное количество предметов для покупки
     */
    data class FromPulseAPI(
        val filters: PulseFilters,
        val maxBudgetCents: Long,
        val maxItemsCount: Int = Int.MAX_VALUE
    ) : ItemSelector

    /**
     * Получить предметы из кастомного источника
     * @param sourceId ID источника (например, "pulse", "custom-api", "database")
     * @param parameters параметры запроса
     * @param maxBudgetCents максимальный бюджет
     */
    data class FromDynamicSource(
        val sourceId: String,
        val parameters: Map<String, String>,
        val maxBudgetCents: Long,
        val maxItemsCount: Int = Int.MAX_VALUE
    ) : ItemSelector

    /**
     * Batch-селектор - выбор нескольких предметов по критериям
     * @param criteria критерии отбора предметов
     * @param maxBudgetCents максимальный бюджет на все предметы
     * @param maxItemsCount максимальное количество предметов
     */
    data class Batch(
        val criteria: ItemCriteria,
        val maxBudgetCents: Long,
        val maxItemsCount: Int = Int.MAX_VALUE
    ) : ItemSelector
}

/**
 * Фильтры для Pulse API
 */
data class PulseFilters(
    val minProfitPercent: Double? = null,
    val minProfitCents: Long? = null,
    val maxPrice: Long? = null,
    val itemTypes: List<String>? = null, // ["knife", "gloves", "rifle", etc.]
    val rarities: List<String>? = null,  // ["covert", "classified", etc.]
    val sourceMarket: Market? = null,
    val targetMarket: Market? = null,
    val floatRange: FloatRange? = null,
    val excludeStattrak: Boolean = false,
    val excludeSouvenir: Boolean = false,
    val minDemand: Int? = null, // Минимальный спрос на предмет
    val customFilters: Map<String, String> = emptyMap()
)

/**
 * Критерии для batch-селектора
 */
data class ItemCriteria(
    val namePatterns: List<String>? = null, // Паттерны имен ["AK-47*", "AWP*"]
    val priceRange: LongRange? = null,
    val floatRange: FloatRange? = null,
    val markets: List<Market>? = null,
    val sortBy: SortCriteria = SortCriteria.PROFIT_DESC,
    val customFilter: ((Map<String, Any>) -> Boolean)? = null
)

data class FloatRange(
    val min: Float = 0f,
    val max: Float = 1f
)

enum class SortCriteria {
    PROFIT_DESC,        // По убыванию прибыли
    PROFIT_ASC,         // По возрастанию прибыли
    PRICE_ASC,          // По возрастанию цены
    PRICE_DESC,         // По убыванию цены
    DEMAND_DESC,        // По убыванию спроса
    FLOAT_ASC,          // По возрастанию float
    FLOAT_DESC          // По убыванию float
}