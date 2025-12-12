package dmitriy.losev.cs.dto.offers.request

data class OffersRequestDTO(
    val authToken: String,
    val templateId: Int,
    val firstMarketOptions: MarketOptionsDTO,
    val secondMarketOptions: MarketOptionsDTO,
    val marketHashNameFilter: String? = null,
    val profitFilter: String? = null,
    val profitPercentFilter: ProfitPercentFilterDTO = ProfitPercentFilterDTO(),
    val counterStrikeItemTypeOptionsDSO: ItemTypeOptionsDTO = ItemTypeOptionsDTO(),
    val dotaItemTypeOptions: ItemTypeOptionsDTO = ItemTypeOptionsDTO(),
    val rustItemTypeOptions: ItemTypeOptionsDTO = ItemTypeOptionsDTO(),
    val salesCountPeriod: String = DEFAULT_SALES_COUNT_PERIOD,
    val salesCountFilters: List<SalesCountFilterDTO> = emptyList(),
    val holdFilter: String? = null,
    val isOverstock: Boolean? = null,
    val displaySoldOutItems: Boolean = false,
    val displayOnlyOverridenItems: Boolean = false,
    val countFilterMode: String = DEFAULT_COUNT_FILTER_MODE,
    val glowOldListItems: Boolean = false,
    val paginationRequest: PaginationRequestDTO = PaginationRequestDTO(),
) {
    companion object {

        private const val DEFAULT_SALES_COUNT_PERIOD = "Week"

        private const val DEFAULT_COUNT_FILTER_MODE = "TotalOffersCount"

    }
}
