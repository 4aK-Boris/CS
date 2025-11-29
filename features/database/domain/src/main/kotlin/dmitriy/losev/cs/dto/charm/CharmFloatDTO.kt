package dmitriy.losev.cs.dto.charm

data class CharmFloatDTO(
    val classId: ULong,
    val instanceId: ULong,
    val itemId: ULong,
    val listingId: ULong,
    val pattern: Int,
    val a: ULong,
    val d: ULong,
    val m: ULong
)