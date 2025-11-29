package dmitriy.losev.cs.dto.sales

import dmitriy.losev.cs.core.Confidence

data class PriceEstimateDTO(
    val price: Double,
    val confidence: Confidence,
    val trend: TrendDTO?
)