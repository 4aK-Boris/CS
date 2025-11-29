package dmitriy.losev.cs.usecases

import dmitriy.losev.cs.dto.item.RequestItemInfoDTO
import org.koin.core.annotation.Factory

@Factory
class GetItemSaleOffersCountUseCase(private val getItemInfoUseCase: GetItemInfoUseCase): BaseUseCase {

    suspend operator fun invoke(itemName: String): Result<Int> = runCatching {
        val requestItemInfo = RequestItemInfoDTO(itemName = itemName)
        getItemInfoUseCase.invoke(requestItemInfo).getOrNull()?.results?.first()?.sellListings ?: 0
    }
}