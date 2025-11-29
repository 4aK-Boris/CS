package dmitriy.losev.cs.dto.sales

import java.time.Instant

data class ItemHistorySalesDTO(
    val time: Instant,
    val id: Int,
    val max: CurrencyDTO,
    val min: CurrencyDTO,
    val avg: CurrencyDTO,
    val avg7: CurrencyDTO,
    val avg30: CurrencyDTO,
    val salesCount7: Int,
    val salesCount30: Int,
    val historySales: List<SaleDTO>
)
