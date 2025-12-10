package dmitriy.losev.cs.dso


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatisticsDSO(

    @SerialName(value = "median_trade_time")
    val medianTradeTime: Int,

    @SerialName(value = "total_avoided_trades")
    val totalAvoidedTrades: Int,

    @SerialName(value = "total_failed_trades")
    val totalFailedTrades: Int,

    @SerialName(value = "total_purchases")
    val totalPurchases: Int,

    @SerialName(value = "total_sales")
    val totalSales: Int,

    @SerialName(value = "total_trades")
    val totalTrades: Int,

    @SerialName(value = "total_verified_trades")
    val totalVerifiedTrades: Int
)
