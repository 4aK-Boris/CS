package dmitriy.losev.cs.mappers.offers.request

import dmitriy.losev.cs.dso.offers.request.OffersRequestDSO
import dmitriy.losev.cs.dto.offers.request.OffersRequestDTO
import org.koin.core.annotation.Factory

@Factory
class OffersRequestMapper(
    private val firstMarketOptionsMapper: FirstMarketOptionsMapper,
    private val secondMarketOptionsMapper: SecondMarketOptionsMapper,
    private val profitPercentFilterMapper: ProfitPercentFilterMapper,
    private val itemTypeOptionsMapper: ItemTypeOptionsMapper,
    private val salesCountFilterMapper: SalesCountFilterMapper,
    private val paginationRequestMapper: PaginationRequestMapper
) {

    fun map(value: OffersRequestDTO): OffersRequestDSO {
        return OffersRequestDSO(
            authToken = value.authToken,
            templateId = value.templateId,
            firstMarketOptions = firstMarketOptionsMapper.map(value = value.firstMarketOptions),
            secondMarketOptions = secondMarketOptionsMapper.map(value = value.secondMarketOptions),
            marketHashNameFilter = value.marketHashNameFilter,
            profitFilter = value.profitFilter,
            profitPercentFilter = profitPercentFilterMapper.map(value = value.profitPercentFilter),
            counterStrikeItemTypeOptionsDSO = itemTypeOptionsMapper.map(value = value.counterStrikeItemTypeOptionsDSO),
            dotaItemTypeOptions = itemTypeOptionsMapper.map(value = value.dotaItemTypeOptions),
            rustItemTypeOptions = itemTypeOptionsMapper.map(value = value.rustItemTypeOptions),
            salesCountPeriod = value.salesCountPeriod,
            salesCountFilters = value.salesCountFilters.map(transform = salesCountFilterMapper::map),
            holdFilter = value.holdFilter,
            isOverstock = value.isOverstock,
            displaySoldOutItems = value.displaySoldOutItems,
            displayOnlyOverridenItems = value.displayOnlyOverridenItems,
            countFilterMode = value.countFilterMode,
            glowOldListItems = value.glowOldListItems,
            paginationRequest = paginationRequestMapper.map(value = value.paginationRequest)
        )
    }
}
