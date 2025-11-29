package dmitriy.losev.cs.dso.sales

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemsIdDSO(

    @SerialName(value = "history")
    val items: Map<String, Int>
)
