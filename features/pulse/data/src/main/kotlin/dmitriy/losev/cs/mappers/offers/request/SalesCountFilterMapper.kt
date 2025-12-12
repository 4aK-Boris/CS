package dmitriy.losev.cs.mappers.offers.request

import dmitriy.losev.cs.dso.offers.request.SalesCountFilterDSO
import dmitriy.losev.cs.dto.offers.request.SalesCountFilterDTO
import org.koin.core.annotation.Factory

@Factory
class SalesCountFilterMapper {

    fun map(value: SalesCountFilterDTO): SalesCountFilterDSO {
        return SalesCountFilterDSO(
            id = value.id,
            market = value.market.title,
            period = value.period,
            salesCount = value.salesCount
        )
    }
}
