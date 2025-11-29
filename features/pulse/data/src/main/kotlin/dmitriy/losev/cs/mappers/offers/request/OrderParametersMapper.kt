package dmitriy.losev.cs.mappers.offers.request

import dmitriy.losev.cs.dso.offers.request.OrderParametersDSO
import dmitriy.losev.cs.dto.offers.request.OrderParametersDTO
import org.koin.core.annotation.Factory

@Factory
class OrderParametersMapper {

    fun map(value: OrderParametersDTO): OrderParametersDSO {
        return OrderParametersDSO(
            key = value.key,
            sortOrder = value.sortOrder.orderName
        )
    }
}