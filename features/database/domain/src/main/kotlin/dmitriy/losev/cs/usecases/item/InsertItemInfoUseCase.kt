package dmitriy.losev.cs.usecases.item

import dmitriy.losev.cs.dto.item.ItemInfoDTO
import dmitriy.losev.cs.exceptions.DatabaseException
import dmitriy.losev.cs.repositories.ItemRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class InsertItemInfoUseCase(@Provided private val itemRepository: ItemRepository): BaseUseCase {

    suspend operator fun invoke(itemInfo: ItemInfoDTO): Result<String> = runCatching {
        itemRepository.insertItemInfo(itemInfo) ?: throw DatabaseException.UnsuccessfulInsertItemInfoException(itemName = itemInfo.name)
    }
}