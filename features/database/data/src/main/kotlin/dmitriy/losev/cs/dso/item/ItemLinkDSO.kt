package dmitriy.losev.cs.dso.item

internal data class ItemLinkDSO(
    val classId: ULong,
    val instanceId: ULong,
    val listingId: ULong,
    val link: String
)
