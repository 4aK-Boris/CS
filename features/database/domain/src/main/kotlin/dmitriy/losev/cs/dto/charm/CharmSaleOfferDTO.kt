package dmitriy.losev.cs.dto.charm

data class CharmSaleOfferDTO(
    val classId: ULong,
    val instanceId: ULong,
    val listingId: ULong,
    val priceForSeller: Int,
    val priceForBuyer: Int,
    val assetId: ULong,
    val link: String
)
