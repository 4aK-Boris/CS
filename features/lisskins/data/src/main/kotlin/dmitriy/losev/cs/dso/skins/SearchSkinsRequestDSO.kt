package dmitriy.losev.cs.dso.skins

data class SearchSkinsRequestDSO(
    val cursor: String? = null,
    val game: String = GAME,
    val floatFrom: Double? = null,
    val floatTo: Double? = null,
    val itemNames: List<String>,
    val onlyUnlocked: Boolean = false,
    val priceFrom: Double? = null,
    val priceTo: Double? = null,
    val sortBy: String,
    val unlockDays: List<Int>? = null
) {

    fun convertToParameters(): Map<String, String> {
        return buildMap {
            cursor?.let { put(key = CURSOR_PARAMETER_KEY, value = cursor) }
            put(key = GAME_PARAMETER_KEY, value = game)
            floatFrom?.let { put(key = FLOAT_FROM_PARAMETER_KEY, floatFrom.toString()) }
            floatTo?.let { put(key = FLOAT_TO_PARAMETER_KEY, floatTo.toString()) }

            itemNames.forEachIndexed { index, name ->
                put(key = "$NAMES_PARAMETER_KEY[$index]", value = name)
            }

            put(key = ONLY_UNLOCKED_PARAMETER_KEY, value = onlyUnlocked.toInt().toString())
            priceFrom?.let { put(key = PRICE_FROM_PARAMETER_KEY, value = priceFrom.toString()) }
            priceTo?.let { put(key = PRICE_TO_PARAMETER_KEY, value = priceTo.toString()) }
            put(key = SORT_BY_PARAMETER_KEY, value = sortBy)

            unlockDays?.forEachIndexed { index, day ->
                put(key = "$UNLOCK_DAYS_PARAMETER_KEY[$index]", value = day.toString())
            }
        }
    }

    private fun Boolean.toInt() = if (this) 1 else 0

    companion object {
        private const val GAME = "csgo"

        private const val CURSOR_PARAMETER_KEY = "cursor"
        private const val GAME_PARAMETER_KEY = "game"
        private const val FLOAT_FROM_PARAMETER_KEY = "float_from"
        private const val FLOAT_TO_PARAMETER_KEY = "float_to"
        private const val NAMES_PARAMETER_KEY = "names"
        private const val ONLY_UNLOCKED_PARAMETER_KEY = "only_unlocked"
        private const val PRICE_FROM_PARAMETER_KEY = "price_from"
        private const val PRICE_TO_PARAMETER_KEY = "price_to"
        private const val SORT_BY_PARAMETER_KEY = "sort_by"
        private const val UNLOCK_DAYS_PARAMETER_KEY = "unlock_days"
    }
}