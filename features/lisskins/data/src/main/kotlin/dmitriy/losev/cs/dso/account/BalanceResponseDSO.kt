package dmitriy.losev.cs.dso.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BalanceResponseDSO(

    @SerialName(value = "data")
    val data: BalanceDSO
)