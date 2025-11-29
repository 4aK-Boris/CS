package dmitriy.losev.cs.dso.item

internal data class ItemSaleOfferDSO(
    val classId: ULong,
    val instanceId: ULong,
    val listingId: ULong,
    val priceForSeller: Int,
    val priceForBuyer: Int,
    val assetId: ULong,
    val link: String
)
