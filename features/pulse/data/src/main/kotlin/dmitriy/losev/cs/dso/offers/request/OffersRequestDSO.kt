package dmitriy.losev.cs.dso.offers.request


import dmitriy.losev.cs.EMPTY_STRING
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class OffersRequestDSO(

    @Transient
    val authToken: String = EMPTY_STRING,

    @SerialName(value = "templateId")
    val templateId: Int = 0,

    @SerialName(value = "firstMarketOptions")
    val firstMarketOptions: FirstMarketOptionsDSO = FirstMarketOptionsDSO(),

    @SerialName(value = "secondMarketOptions")
    val secondMarketOptions: SecondMarketOptionsDSO = SecondMarketOptionsDSO(),

    @SerialName(value = "marketHashNameFilter")
    val marketHashNameFilter: String? = null,

    @SerialName(value = "profitFilter")
    val profitFilter: String? = null,

    @SerialName(value = "profitPercentFilter")
    val profitPercentFilter: ProfitPercentFilterDSO = ProfitPercentFilterDSO(),

    @SerialName(value = "counterStrikeItemTypeOptions")
    val counterStrikeItemTypeOptionsDSO: ItemTypeOptionsDSO = ItemTypeOptionsDSO(),

    @SerialName(value = "dotaItemTypeOptions")
    val dotaItemTypeOptions: ItemTypeOptionsDSO = ItemTypeOptionsDSO(),

    @SerialName(value = "rustItemTypeOptions")
    val rustItemTypeOptions: ItemTypeOptionsDSO = ItemTypeOptionsDSO(),

    @SerialName(value = "salesCountPeriod")
    val salesCountPeriod: String = DEFAULT_SALES_COUNT_PERIOD,

    @SerialName(value = "salesCountFilters")
    val salesCountFilters: List<SalesCountFilterDSO> = emptyList(),

    @SerialName(value = "holdFilter")
    val holdFilter: String? = null,

    @SerialName(value = "isOverstock")
    val isOverstock: Boolean? = null,

    @SerialName(value = "displaySoldOutItems")
    val displaySoldOutItems: Boolean = false,

    @SerialName(value = "displayOnlyOverridenItems")
    val displayOnlyOverridenItems: Boolean = false,

    @SerialName(value = "countFilterMode")
    val countFilterMode: String = DEFAULT_COUNT_FILTER_MODE,

    @SerialName(value = "glowOldListItems")
    val glowOldListItems: Boolean = false,

    @SerialName(value = "paginationRequest")
    val paginationRequest: PaginationRequestDSO = PaginationRequestDSO(),
) {
    companion object {

        private const val DEFAULT_SALES_COUNT_PERIOD = "Week"

        private const val DEFAULT_COUNT_FILTER_MODE = "TotalOffersCount"

    }
}
