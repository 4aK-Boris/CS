package dmitriy.losev.cs.core.graph.market

import dmitriy.losev.cs.core.graph.context.PurchasedItem
import dmitriy.losev.cs.core.graph.result.SaleResult
import dmitriy.losev.cs.core.graph.strategy.ItemSelector
import dmitriy.losev.cs.core.graph.strategy.PriceConstraint
import dmitriy.losev.cs.core.graph.strategy.PriceStrategy
import dmitriy.losev.cs.core.market.Market


/**
 * Интерфейс для операций с маркетплейсами
 * Реализации должны быть в data слое
 */
interface MarketOperations {

    /**
     * Покупка предмета на маркетплейсе
     */
    suspend fun buyItem(
        steamId: Long,
        market: Market,
        itemSelector: ItemSelector,
        maxPrice: PriceConstraint
    ): PurchasedItem

    /**
     * Продажа предмета на маркетплейсе
     */
    suspend fun sellItem(
        steamId: Long,
        market: Market,
        item: PurchasedItem,
        priceStrategy: PriceStrategy
    ): SaleResult

    /**
     * Ожидание окончания трейд-лока у предмета
     * @param item предмет, у которого ждем окончания трейд-лока
     */
    suspend fun waitForTradeLock(item: PurchasedItem)

    /**
     * Ожидание получения предмета в инвентарь
     */
    suspend fun waitForItemInInventory(steamId: Long, item: PurchasedItem)

    /**
     * Ожидание завершения продажи (когда кто-то купит предмет)
     */
    suspend fun waitForSaleCompleted(steamId: Long, saleId: String)

    /**
     * Ожидание разблокировки денег после продажи на маркетплейсе
     * На Steam и многих других маркетах деньги блокируются на 7-8 дней после продажи
     *
     * @param steamId SteamId аккаунта
     * @param market маркетплейс, на котором была продажа
     * @param saleId ID продажи
     * @param minDaysToWait минимальное количество дней ожидания (если null, берется из конфига маркета)
     */
    suspend fun waitForFundsUnlocked(
        steamId: Long,
        market: Market,
        saleId: String,
        minDaysToWait: Int? = null
    )

    /**
     * Ожидание пока баланс на маркете достигнет минимального значения
     * Используется для накопления средств перед покупкой
     *
     * @param steamId SteamId аккаунта
     * @param market маркетплейс для проверки баланса
     * @param minAmountCents минимальная сумма в копейках
     */
    suspend fun waitForBalanceAvailable(
        steamId: Long,
        market: Market,
        minAmountCents: Long
    )

    /**
     * Ожидание окончания кулдауна для операций на маркете
     * Некоторые маркеты имеют ограничения на частоту операций
     *
     * @param steamId SteamId аккаунта
     * @param market маркетплейс
     * @param operationType тип операции (buy, sell, withdraw)
     */
    suspend fun waitForMarketCooldown(
        steamId: Long,
        market: Market,
        operationType: String
    )

    /**
     * Кастомное условие ожидания с проверочной функцией
     * Реализация должна периодически проверять условие и ждать его выполнения
     *
     * @param steamId SteamId аккаунта
     * @param checkInterval интервал проверки в секундах
     * @param maxWaitSeconds максимальное время ожидания
     * @param description описание условия для логов
     */
    suspend fun waitForCustomCondition(
        steamId: Long,
        checkInterval: Long,
        maxWaitSeconds: Long,
        description: String
    )

    /**
     * Получение текущего баланса на маркете
     */
    suspend fun getBalance(steamId: Long, market: Market): Long

    /**
     * Проверка, разблокированы ли средства после продажи
     */
    suspend fun areFundsUnlocked(steamId: Long, market: Market, saleId: String): Boolean

    // ========== Batch операции ==========

    /**
     * Выбор предметов для batch-покупки на основе селектора
     * Для динамических селекторов (FromPulseAPI, FromDynamicSource) - делает запрос к API
     * Возвращает список предметов, которые можно купить в рамках бюджета
     */
    suspend fun selectItemsForBatchBuy(
        steamId: Long,
        market: Market,
        itemSelector: ItemSelector,
        maxPrice: PriceConstraint
    ): List<ItemToBuy>

    /**
     * Покупка конкретного предмета по ID
     */
    suspend fun buyItemById(
        steamId: Long,
        market: Market,
        itemId: String,
        priceCents: Long
    ): PurchasedItem

    /**
     * Параллельная покупка нескольких предметов
     */
    suspend fun buyItemsInParallel(
        steamId: Long,
        market: Market,
        items: List<ItemToBuy>
    ): List<PurchasedItem>

    /**
     * Параллельная продажа нескольких предметов
     */
    suspend fun sellItemsInParallel(
        steamId: Long,
        market: Market,
        items: List<PurchasedItem>,
        priceStrategy: PriceStrategy
    ): List<SaleResult>
}