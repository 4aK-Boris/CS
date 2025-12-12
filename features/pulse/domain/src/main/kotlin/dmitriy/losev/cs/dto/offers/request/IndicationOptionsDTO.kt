package dmitriy.losev.cs.dto.offers.request

data class IndicationOptionsDTO(
    val isEnabled: Boolean = false,
    val colorIndicators: List<String> = emptyList(),
)
