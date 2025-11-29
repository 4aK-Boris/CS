package dmitriy.losev.cs.usecases

import dmitriy.losev.cs.dto.item.ItemInfoDTO
import dmitriy.losev.cs.dto.item.ItemLinkDTO
import dmitriy.losev.cs.usecases.item.InsertItemSaleOffersUseCase
import org.koin.core.annotation.Factory

@Factory
class GetAndSaveSaleOffersForItemUseCase(
    private val getSaleOffersForItemUseCase: GetSaleOffersForItemUseCase,
    private val insertItemSaleOffersUseCase: InsertItemSaleOffersUseCase
) : BaseUseCase {

    suspend operator fun invoke(
        itemInfo: ItemInfoDTO,
        startPosition: Int,
        requestCount: Int = 100
    ): Result<List<ItemLinkDTO>> {
        return getSaleOffersForItemUseCase.invoke(itemName = itemInfo.name, startPosition = startPosition, requestCount = requestCount).mapCatchingInData { listingsInfo ->
            val items = listingsInfo.map { listingInfo ->
                Triple(
                    first = itemInfo.classId,
                    second = itemInfo.instanceId,
                    third = listingInfo
                )
            }
            insertItemSaleOffersUseCase.invoke(items = items)
        }
    }
}