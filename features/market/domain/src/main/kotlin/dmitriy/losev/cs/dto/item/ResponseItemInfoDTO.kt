package dmitriy.losev.cs.dto.item

data class ResponseItemInfoDTO(
    val success: Boolean,
    val start: Int,
    val pagesize: Int,
    val totalCount: Int,
    val searchData: SearchDataDTO,
    val results: List<ResultDTO>
)


