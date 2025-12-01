package dmitriy.losev.cs.usecases.item

import dmitriy.losev.cs.dto.item.ItemInfoDTO
import dmitriy.losev.cs.repositories.ItemRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided


@Factory
class GetItemsInfoUseCase(@Provided private val itemRepository: ItemRepository): BaseUseCase {

    suspend operator fun invoke(delay: Long, count: Int): Result<List<ItemInfoDTO>> = runCatching {
        itemRepository.getItemsInfo(delay, count)
    }
}
