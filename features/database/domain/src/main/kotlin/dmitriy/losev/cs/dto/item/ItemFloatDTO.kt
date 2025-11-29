package dmitriy.losev.cs.dto.item

data class ItemFloatDTO(
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