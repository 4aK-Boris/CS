package dmitriy.losev.cs.core.graph.strategy

import dmitriy.losev.cs.core.market.Market
import java.time.Instant

sealed interface WaitCondition {
    /**
     * Простое ожидание заданное количество секунд
     */
    data class Duration(val seconds: Long) : WaitCondition

    /**
     * Ожидание окончания трейд-лока у купленного предмета
     * Используется после покупки на маркетах где есть трейд-лок (например, LisSkins)
     */
    data class TradeLockExpired(val itemRef: String) : WaitCondition

    /**
     * Ожидание получения предмета в инвентарь
     * Используется после покупки для проверки что предмет действительно получен
     */
    data class ItemInInventory(val itemRef: String) : WaitCondition

    /**
     * Ожидание завершения продажи (когда кто-то купит предмет)
     * Используется после выставления предмета на продажу
     */
    data class SaleCompleted(val saleRef: String) : WaitCondition

    /**
     * Ожидание разблокировки денег после продажи на маркетплейсе
     * На многих маркетах (Steam, LisSkins и др.) деньги блокируются на 7-8 дней после продажи
     *
     * @param saleRef ссылка на узел продажи
     * @param market маркетплейс, на котором была продажа (для определения срока блокировки)
     * @param minDaysToWait минимальное количество дней ожидания (по умолчанию берется из конфига маркета)
     */
    data class FundsUnlocked(
        val saleRef: String,
        val market: Market,
        val minDaysToWait: Int? = null
    ) : WaitCondition

    /**
     * Ожидание пока баланс на маркете достигнет минимального значения
     * Используется для накопления средств перед покупкой
     *
     * @param market маркетплейс для проверки баланса
     * @param minAmountCents минимальная сумма в копейках
     */
    data class BalanceAvailable(
        val market: Market,
        val minAmountCents: Long
    ) : WaitCondition

    /**
     * Ожидание до определенной даты/времени
     * Полезно для планирования покупок/продаж в определенное время
     *
     * @param until дата и время до которого нужно ждать
     */
    data class UntilDateTime(val until: Instant) : WaitCondition

    /**
     * Ожидание окончания кулдауна для операций на маркете
     * Некоторые маркеты имеют ограничения на частоту операций
     *
     * @param market маркетплейс
     * @param operationType тип операции (buy, sell, withdraw)
     */
    data class MarketCooldownExpired(
        val market: Market,
        val operationType: String = "any"
    ) : WaitCondition

    /**
     * Комбинированное условие - ждать выполнения всех условий
     *
     * @param conditions список условий, все должны быть выполнены
     */
    data class All(val conditions: List<WaitCondition>) : WaitCondition

    /**
     * Комбинированное условие - ждать выполнения любого из условий
     *
     * @param conditions список условий, достаточно выполнения любого
     */
    data class Any(val conditions: List<WaitCondition>) : WaitCondition

    /**
     * Кастомное условие ожидания с проверочной функцией
     *
     * @param checkInterval интервал проверки в секундах
     * @param maxWaitSeconds максимальное время ожидания
     * @param description описание условия для логов
     */
    data class Custom(
        val checkInterval: Long = 60,
        val maxWaitSeconds: Long = 3600,
        val description: String = "Custom wait condition"
    ) : WaitCondition
}