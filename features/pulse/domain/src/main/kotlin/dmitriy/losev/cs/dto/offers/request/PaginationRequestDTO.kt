package dmitriy.losev.cs.dto.offers.request

data class PaginationRequestDTO(
    val orderParameters: OrderParametersDTO? = OrderParametersDTO(),
    val skipCount: Int = 0,
    val takeCount: Int = 100
)