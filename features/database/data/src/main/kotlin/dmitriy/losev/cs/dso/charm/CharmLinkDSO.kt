package dmitriy.losev.cs.dso.charm

internal data class CharmLinkDSO(
    val classId: Long,
    val instanceId: Long,
    val listingId: Long,
    val link: String
)