package dmitriy.losev.cs.dso.sales

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyDSO(

    @SerialName(value = "RUB")
    val rub: Double,

    @SerialName(value = "USD")
    val usd: Double,

    @SerialName(value = "EUR")
    val eur: Double
)
