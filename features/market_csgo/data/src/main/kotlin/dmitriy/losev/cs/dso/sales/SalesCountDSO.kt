package dmitriy.losev.cs.dso.sales

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SalesCountDSO(

    @SerialName(value = "USD")
    val count: Int
)
