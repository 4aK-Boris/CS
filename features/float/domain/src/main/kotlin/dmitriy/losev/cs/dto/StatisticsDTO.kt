package dmitriy.losev.cs.dto

data class StatisticsDTO(
    val botsOnline: Int = 0,
    val botsTotal: Int = 0,
    val queueSize: Int = Int.MAX_VALUE,
    val queueConcurrency: Int = 0
)