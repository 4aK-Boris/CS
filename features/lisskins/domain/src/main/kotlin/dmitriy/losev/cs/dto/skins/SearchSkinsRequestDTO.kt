package dmitriy.losev.cs.dto.skins

import dmitriy.losev.cs.core.SortSkinsOrder

data class SearchSkinsRequestDTO(
    val cursor: String? = null,
    val game: String = GAME,
    val floatFrom: Double? = null,
    val floatTo: Double? = null,
    val itemNames: List<String>,
    val onlyUnlocked: Boolean = false,
    val priceFrom: Double? = null,
    val priceTo: Double? = null,
    val sortBy: SortSkinsOrder = SortSkinsOrder.LOWEST_PRICE,
    val unlockDays: List<Int>? = null
) {

    companion object {
        private const val GAME = "csgo"
    }
}