package dmitriy.losev.cs.mappers.offers.request

import dmitriy.losev.cs.dso.offers.request.PaginationRequestDSO
import dmitriy.losev.cs.dto.offers.request.PaginationRequestDTO
import org.koin.core.annotation.Factory

@Factory
class PaginationRequestMapper(
    private val orderParametersMapper: OrderParametersMapper
) {

    fun map(value: PaginationRequestDTO): PaginationRequestDSO {
        return PaginationRequestDSO(
            orderParameters = value.orderParameters?.let(block = orderParametersMapper::map),
            skipCount = value.skipCount,
            takeCount = value.takeCount
        )
    }
}
