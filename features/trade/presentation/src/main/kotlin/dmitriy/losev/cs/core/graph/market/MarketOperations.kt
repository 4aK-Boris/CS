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
        steamId: ULong,
        market: Market,
        itemSelector: ItemSelector,
        maxPrice: PriceConstraint
    ): PurchasedItem

    /**
     * Продажа предмета на маркетплейсе
     */
    suspend fun sellItem(
        steamId: ULong,
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
    suspend fun waitForItemInInventory(steamId: ULong, item: PurchasedItem)

    /**
     * Ожидание завершения продажи (когда кто-то купит предмет)
     */
    suspend fun waitForSaleCompleted(steamId: ULong, saleId: String)

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
        steamId: ULong,
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
        steamId: ULong,
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
        steamId: ULong,
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
        steamId: ULong,
        checkInterval: Long,
        maxWaitSeconds: Long,
        description: String
    )

    /**
     * Получение текущего баланса на маркете
     */
    suspend fun getBalance(steamId: ULong, market: Market): Long

    /**
     * Проверка, разблокированы ли средства после продажи
     */
    suspend fun areFundsUnlocked(steamId: ULong, market: Market, saleId: String): Boolean

    // ========== Batch операции ==========

    /**
     * Выбор предметов для batch-покупки на основе селектора
     * Для динамических селекторов (FromPulseAPI, FromDynamicSource) - делает запрос к API
     * Возвращает список предметов, которые можно купить в рамках бюджета
     */
    suspend fun selectItemsForBatchBuy(
        steamId: ULong,
        market: Market,
        itemSelector: ItemSelector,
        maxPrice: PriceConstraint
    ): List<ItemToBuy>

    /**
     * Покупка конкретного предмета по ID
     */
    suspend fun buyItemById(
        steamId: ULong,
        market: Market,
        itemId: String,
        priceCents: Long
    ): PurchasedItem

    /**
     * Параллельная покупка нескольких предметов
     */
    suspend fun buyItemsInParallel(
        steamId: ULong,
        market: Market,
        items: List<ItemToBuy>
    ): List<PurchasedItem>

    /**
     * Параллельная продажа нескольких предметов
     */
    suspend fun sellItemsInParallel(
        steamId: ULong,
        market: Market,
        items: List<PurchasedItem>,
        priceStrategy: PriceStrategy
    ): List<SaleResult>
}