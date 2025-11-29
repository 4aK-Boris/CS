package dmitriy.losev.cs.dso

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatisticsDSO(

    @SerialName(value = "bots_online")
    val botsOnline: Int,

    @SerialName(value = "bots_total")
    val botsTotal: Int,

    @SerialName(value = "queue_size")
    val queueSize: Int,

    @SerialName(value = "queue_concurrency")
    val queueConcurrency: Int
)