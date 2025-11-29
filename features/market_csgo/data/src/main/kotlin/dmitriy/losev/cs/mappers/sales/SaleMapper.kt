package dmitriy.losev.cs.mappers.sales

import dmitriy.losev.cs.dto.sales.SaleDTO
import org.koin.core.annotation.Factory

@Factory
internal class SaleMapper(private val instantMapper: InstantMapper) {

    fun map(value: List<Double>): SaleDTO {
        return SaleDTO(
            price = value[2],
            timestamp = instantMapper.map(value = value[0].toLong())
        )
    }
}