package dmitriy.losev.cs.usecases.item

import dmitriy.losev.cs.dto.ListingInfoDTO
import dmitriy.losev.cs.dto.item.ItemLinkDTO
import dmitriy.losev.cs.repositories.ItemRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class InsertItemSaleOffersUseCase(@Provided private val itemRepository: ItemRepository): BaseUseCase {

    suspend operator fun invoke(items: List<Triple<ULong, ULong, ListingInfoDTO>>): Result<List<ItemLinkDTO>> = runCatching {
        itemRepository.insertItemSaleOffers(items)
    }
}
