package dmitriy.losev.cs.mappers.sales

import dmitriy.losev.cs.dso.sales.ItemHistorySalesDSO
import dmitriy.losev.cs.dto.sales.ItemHistorySalesDTO
import org.koin.core.annotation.Factory

@Factory
internal class ItemHistorySalesMapper(
    private val instantMapper: InstantMapper,
    private val saleMapper: SaleMapper,
    private val currencyMapper: CurrencyMapper
) {

    fun map(value: ItemHistorySalesDSO): ItemHistorySalesDTO {
        return ItemHistorySalesDTO(
            time = instantMapper.map(value = value.time),
            id = value.data.id,
            min = currencyMapper.map(value = value.data.min),
            max = currencyMapper.map(value = value.data.max),
            avg = currencyMapper.map(value = value.data.avg),
            avg7 = currencyMapper.map(value = value.data.avg7),
            avg30 = currencyMapper.map(value = value.data.avg30),
            salesCount7 = value.data.salesCount7.count,
            salesCount30 = value.data.salesCount30.count,
            historySales = value.data.historySales.map(transform = saleMapper::map)
        )
    }
}