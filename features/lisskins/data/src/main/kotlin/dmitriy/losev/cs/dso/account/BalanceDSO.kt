package dmitriy.losev.cs.dso.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BalanceDSO(

    @SerialName(value = "balance")
    val balance: Double
)
