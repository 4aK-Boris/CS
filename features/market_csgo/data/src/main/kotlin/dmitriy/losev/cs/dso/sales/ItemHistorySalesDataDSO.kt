package dmitriy.losev.cs.dso.sales

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemHistorySalesDataDSO(

    @SerialName(value = "id")
    val id: Int,

    @SerialName(value = "max")
    val max: CurrencyDSO,

    @SerialName(value = "min")
    val min: CurrencyDSO,

    @SerialName(value = "average")
    val avg: CurrencyDSO,

    @SerialName(value = "average7d")
    val avg7: CurrencyDSO,

    @SerialName(value = "average30d")
    val avg30: CurrencyDSO,

    @SerialName(value = "sales7d")
    val salesCount7: SalesCountDSO,

    @SerialName(value = "sales30d")
    val salesCount30: SalesCountDSO,

    @SerialName(value = "history")
    val historySales: List<List<Double>>
)
