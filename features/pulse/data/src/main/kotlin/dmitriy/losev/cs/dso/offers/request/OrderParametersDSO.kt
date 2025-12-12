package dmitriy.losev.cs.dso.offers.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderParametersDSO(

    @SerialName(value = "key")
    val key: String = DEFAULT_KEY,

    @SerialName(value = "sortOrder")
    val sortOrder: String = DEFAULT_SORTING_ORDER
) {

    companion object {

        private const val DEFAULT_KEY = "profitPercent"
        private const val DEFAULT_SORTING_ORDER = "Descending"
    }
}
