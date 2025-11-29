package dmitriy.losev.cs.dto.skins

data class SearchSkinsResponseDTO(
    val skins: List<SkinDTO>,
    val count: Int,
    val nextCursor: String
)