package dmitriy.losev.cs.dto

data class ListingInfoDTO(
    val listingId: ULong,
    val priceForSeller: Int,
    val priceForBuyer: Int,
    val assetId: ULong,
    val link: String
)