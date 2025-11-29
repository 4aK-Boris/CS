package dmitriy.losev.cs.dto.skins

data class BuySkinForUserRequestDTO(
    val ids: List<Int>,
    val partner: String,
    val token: String,
    val maxPrice: Int,
    val customId: Long,
    val skipUnavailable: Boolean = true
)