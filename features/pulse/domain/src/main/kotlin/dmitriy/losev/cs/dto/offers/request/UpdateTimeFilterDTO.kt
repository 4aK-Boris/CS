package dmitriy.losev.cs.dto.offers.request

data class UpdateTimeFilterDTO(
    val minTime: Long? = null,
    val maxTime: Long? = null
)
