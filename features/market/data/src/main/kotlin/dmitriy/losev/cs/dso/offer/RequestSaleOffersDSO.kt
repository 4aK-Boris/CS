package dmitriy.losev.cs.dso.offer

data class RequestSaleOffersDSO(
    val query: String,
    private val start: String,
    private val count: String,
    private val currency: Int = 1,
    private val language: String = LANGUAGE
) {

    fun toParams(): Map<String, String> = mapOf(
        START_PARAM_KEY to start,
        COUNT_PARAM_KEY to count,
        CURRENCY_PARAM_KEY to currency.toString(),
        LANGUAGE_PARAM_KEY to language
    )

    companion object {
        private const val LANGUAGE = "english"

        private const val START_PARAM_KEY = "start"
        private const val COUNT_PARAM_KEY = "count"
        private const val CURRENCY_PARAM_KEY = "currency"
        private const val LANGUAGE_PARAM_KEY = "language"
    }
}