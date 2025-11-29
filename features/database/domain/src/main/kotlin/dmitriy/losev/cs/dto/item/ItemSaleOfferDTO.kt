package dmitriy.losev.cs.dto.item

data class ItemSaleOfferDTO(
    val classId: ULong,
    val instanceId: ULong,
    val listingId: ULong,
    val priceForSeller: Int,
    val priceForBuyer: Int,
    val assetId: ULong,
    val link: String
)
