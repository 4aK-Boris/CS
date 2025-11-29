package dmitriy.losev.cs.dto.item

data class ResultDTO(
    val name: String,
    val sellListings: Int,
    val sellPrice: Int,
    val assetDescription: AssetDescriptionDTO
)