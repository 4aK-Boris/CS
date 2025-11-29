package dmitriy.losev.cs.dto.sales

import dmitriy.losev.cs.core.TrendDirection

data class TrendDTO(
    val direction: TrendDirection,
    val dailyChangePercent: Double,  // изменение в % за день
    val strength: Double             // R² — насколько тренд выражен (0..1)
)