package dmitriy.losev.cs.core

enum class ItemType(val type: String) {
    COVERT_RIFLE(type = "Covert Rifle"),
    UNKNOWN_ITEM(type = "Unknown Item Type");

    companion object {

        fun getItemTypeByName(type: String): ItemType {
            return ItemType.entries.find { it.type == type } ?: ItemType.UNKNOWN_ITEM
        }
    }
}