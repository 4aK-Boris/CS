package dmitriy.losev.cs.dso.offers.request


import dmitriy.losev.cs.EMPTY_STRING
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class SecondMarketOptionsDSO(

    @Transient
    val marketName: String = EMPTY_STRING,

    @SerialName(value = "secondMarketCountFilter")
    val marketCountFilter: MarketCountFilterDSO = MarketCountFilterDSO(),

    @SerialName(value = "secondMarketPriceFilter")
    val marketPriceFilter: MarketPriceFilterDSO = MarketPriceFilterDSO(),

    @SerialName(value = "updateTimeFilter")
    val updateTimeFilter: UpdateTimeFilterDSO = UpdateTimeFilterDSO(),

    @SerialName(value = "secondMarketPriceType")
    val marketPriceType: String = DEFAULT_MARKET_PRICE_TYPE,
) {
    companion object {

        private const val DEFAULT_MARKET_PRICE_TYPE = "Sell"
    }
}
