package dmitriy.losev.cs.dto.item

data class SearchDataDTO(
    val query: String,
    val totalCount: Int,
    val pagesize: Int
)