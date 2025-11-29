package dmitriy.losev.cs.dso.sales

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemHistorySalesDSO(

    @SerialName(value = "time")
    val time: Long,

    @SerialName(value = "data")
    val data: ItemHistorySalesDataDSO
)
