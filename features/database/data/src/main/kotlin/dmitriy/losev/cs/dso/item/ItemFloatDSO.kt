package dmitriy.losev.cs.dso.item

internal data class ItemFloatDSO(
    val classId: ULong,
    val instanceId: ULong,
    val itemId: ULong,
    val listingId: ULong,
    val float: Double,
    val pattern: Int,
    val s: ULong,
    val a: ULong,
    val d: ULong,
    val m: ULong
)