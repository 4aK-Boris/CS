package dmitriy.losev.cs.dso.charm

internal data class CharmSaleOfferDSO(
    val classId: Long,
    val instanceId: Long,
    val listingId: Long,
    val priceForSeller: Int,
    val priceForBuyer: Int,
    val assetId: Long,
    val link: String
)
