package dmitriy.losev.cs.core.graph.context

import dmitriy.losev.cs.core.graph.result.SaleResult
import kotlinx.coroutines.Job

sealed interface NodeResult {
    // Одиночные операции
    data class BuySuccess(val item: PurchasedItem) : NodeResult
    data class SellSuccess(val saleId: String, val priceCents: Long) : NodeResult
    data class WaitCompleted(val durationMs: Long) : NodeResult

    // Batch операции
    data class BatchBuySuccess(
        val items: List<PurchasedItem>,
        val totalSpentCents: Long
    ) : NodeResult

    data class BatchSellSuccess(
        val sales: List<SaleResult>,
        val totalEarnedCents: Long
    ) : NodeResult

    data class PoolOperationSuccess(val poolId: String) : NodeResult

    // Асинхронные операции - узел продолжает работать в фоне
    data class InProgress(
        val nodeId: String,
        val job: Job,  // Корутина, выполняющая операцию
        val progress: OperationProgress = OperationProgress()
    ) : NodeResult

    // Барьер - ожидание завершения других узлов
    data class BarrierCompleted(
        val waitedNodes: List<String>,
        val durationMs: Long
    ) : NodeResult

    object Failed : NodeResult
}
