package dmitriy.losev.cs.dto.item

import dmitriy.losev.cs.core.ItemType

data class AssetDescriptionDTO(
    val classId: ULong,
    val instanceId: ULong,
    val tradable: Int,
    val type: ItemType,
    val marketHashName: String,
)