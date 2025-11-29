package dmitriy.losev.cs.dto.offers.request

import dmitriy.losev.cs.pulse.SortingOrder

data class OrderParametersDTO(
    val key: String = DEFAULT_KEY,
    val sortOrder: SortingOrder = SortingOrder.DESCENDING,
) {

    companion object {

        private const val DEFAULT_KEY = "profitPercent"
    }
}