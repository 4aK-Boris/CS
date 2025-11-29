package dmitriy.losev.cs.dso.offers.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginationRequestDSO(

    @SerialName("orderParameters")
    val orderParameters: OrderParametersDSO? = OrderParametersDSO(),

    @SerialName(value = "skipCount")
    val skipCount: Int = 0,

    @SerialName(value = "takeCount")
    val takeCount: Int = 100
)