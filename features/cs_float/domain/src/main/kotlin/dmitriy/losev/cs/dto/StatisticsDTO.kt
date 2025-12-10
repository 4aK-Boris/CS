package dmitriy.losev.cs.dto

data class StatisticsDTO(
    val medianTradeTime: Int,
    val totalAvoidedTrades: Int,
    val totalFailedTrades: Int,
    val totalPurchases: Int,
    val totalSales: Int,
    val totalTrades: Int,
    val totalVerifiedTrades: Int
)
