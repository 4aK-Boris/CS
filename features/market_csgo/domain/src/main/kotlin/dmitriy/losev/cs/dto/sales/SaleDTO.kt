package dmitriy.losev.cs.dto.sales

import java.time.Instant

data class SaleDTO(
    val price: Double,
    val timestamp: Instant
)