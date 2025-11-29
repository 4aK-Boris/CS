package dmitriy.losev.cs.dto.item

data class RequestItemInfoDTO(
    val itemName: String,
    val start: Int = 0,
    val count: Int = 100
)


