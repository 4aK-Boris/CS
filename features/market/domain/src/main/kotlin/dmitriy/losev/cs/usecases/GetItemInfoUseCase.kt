package dmitriy.losev.cs.usecases

import dmitriy.losev.cs.dto.item.RequestItemInfoDTO
import dmitriy.losev.cs.dto.item.ResponseItemInfoDTO
import dmitriy.losev.cs.repositories.MarketItemRepository
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetItemInfoUseCase(@Provided private val marketItemRepository: MarketItemRepository): BaseUseCase {

    suspend operator fun invoke(requestItemInfo: RequestItemInfoDTO): Result<ResponseItemInfoDTO> = runCatching {
        marketItemRepository.getItemInfo(requestItemInfo)
    }
}