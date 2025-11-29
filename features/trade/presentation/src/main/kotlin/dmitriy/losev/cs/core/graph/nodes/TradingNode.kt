package dmitriy.losev.cs.core.graph.nodes

import dmitriy.losev.cs.core.graph.context.ExecutionContext
import dmitriy.losev.cs.core.graph.strategy.ItemSelector
import dmitriy.losev.cs.core.graph.strategy.PriceConstraint
import dmitriy.losev.cs.core.graph.strategy.PriceStrategy
import dmitriy.losev.cs.core.graph.strategy.WaitCondition
import dmitriy.losev.cs.core.market.Market

sealed interface TradingNode {

    val id: String
    val next: List<Edge>

    data class BuyNode(
        override val id: String,
        val market: Market,
        val itemSelector: ItemSelector,  // что покупаем
        val maxPrice: PriceConstraint,
        val waitForCompletion: Boolean = true,  // Ждать завершения перед переходом к следующему узлу
        override val next: List<Edge> = emptyList()
    ) : TradingNode

    data class SellNode(
        override val id: String,
        val market: Market,
        val itemRef: String,  // ссылка на результат предыдущего узла
        val priceStrategy: PriceStrategy,
        val waitForCompletion: Boolean = true,  // Ждать завершения перед переходом к следующему узлу
        override val next: List<Edge> = emptyList()
    ) : TradingNode

    data class WaitNode(
        override val id: String,
        val condition: WaitCondition,
        override val next: List<Edge> = emptyList()
    ) : TradingNode

    data class ConditionNode(
        override val id: String,
        val check: (ExecutionContext) -> Boolean,
        val onTrue: String,   // id узла если true
        val onFalse: String   // id узла если false
    ) : TradingNode {
        override val next: List<Edge>
            get() = listOf(
                Edge(onTrue, EdgeCondition.When { check(it) }),
                Edge(onFalse, EdgeCondition.When { !check(it) })
            )
    }

    // Batch-операции для работы с несколькими предметами
    data class BatchBuyNode(
        override val id: String,
        val market: Market,
        val itemSelector: ItemSelector,
        val maxPrice: PriceConstraint,
        val executionMode: ExecutionMode = ExecutionMode.SEQUENTIAL,
        val continuousBuying: Boolean = false,  // Продолжать покупать в фоне
        val waitForCompletion: Boolean = true,  // Ждать завершения всех покупок
        val poolId: String = "default",
        override val next: List<Edge> = emptyList()
    ) : TradingNode

    data class BatchSellNode(
        override val id: String,
        val market: Market,
        val itemPoolRef: String,
        val priceStrategy: PriceStrategy,
        val executionMode: ExecutionMode = ExecutionMode.SEQUENTIAL,
        val sellCondition: SellCondition = SellCondition.All,
        val continuousSelling: Boolean = false,  // Продолжать продавать в фоне по мере разблокировки
        val waitForCompletion: Boolean = true,   // Ждать завершения всех продаж
        override val next: List<Edge> = emptyList()
    ) : TradingNode

    data class ItemPoolNode(
        override val id: String,
        val poolId: String,
        val operation: PoolOperation,
        val condition: PoolCondition? = null,
        val waitForCompletion: Boolean = true,  // Ждать завершения операции с пулом
        override val next: List<Edge> = emptyList()
    ) : TradingNode

    /**
     * Барьерный узел - ждет завершения нескольких других узлов
     * Используется для синхронизации параллельных операций
     *
     * Пример: Продажа идет в фоне (waitForCompletion=false),
     * но перед следующей покупкой нужно дождаться ВСЕХ денег - используем барьер
     */
    data class BarrierNode(
        override val id: String,
        val waitFor: List<String>,  // ID узлов, завершения которых нужно дождаться
        val waitCondition: WaitCondition? = null,  // Опциональное доп. условие
        override val next: List<Edge> = emptyList()
    ) : TradingNode

    // Начальный и конечный узлы
    data class StartNode(
        override val id: String = "start",
        override val next: List<Edge>
    ) : TradingNode

    data class EndNode(
        override val id: String = "end"
    ) : TradingNode {
        override val next: List<Edge> = emptyList()
    }
}