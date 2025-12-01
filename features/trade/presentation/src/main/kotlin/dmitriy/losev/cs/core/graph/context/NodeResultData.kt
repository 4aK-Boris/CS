package dmitriy.losev.cs.core.graph.context

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface NodeResultData {
    @Serializable
    @SerialName("buy_success")
    data class BuySuccess(
        val assetId: String,
        val classId: String,
        val instanceId: String,
        val marketHashName: String,
        val pricePaidCents: Long,
        val floatValue: Float? = null,
        val tradeLockUntil: Long? = null  // epoch millis
    ) : NodeResultData

    @Serializable
    @SerialName("sell_success")
    data class SellSuccess(
        val saleId: String,
        val priceCents: Long,
        val market: String
    ) : NodeResultData

    @Serializable
    @SerialName("transfer_success")
    data class TransferSuccess(
        val tradeOfferId: String,
        val toAccountId: String
    ) : NodeResultData

    @Serializable
    @SerialName("wait_completed")
    data class WaitCompleted(
        val waitedMs: Long
    ) : NodeResultData
}