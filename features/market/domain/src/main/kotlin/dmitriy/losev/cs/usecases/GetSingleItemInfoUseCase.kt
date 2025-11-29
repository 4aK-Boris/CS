package dmitriy.losev.cs.usecases

import dmitriy.losev.cs.dto.item.RequestItemInfoDTO
import dmitriy.losev.cs.dto.item.ResponseItemInfoDTO
import org.koin.core.annotation.Factory

@Factory
class GetSingleItemInfoUseCase(private val getItemInfoUseCase: GetItemInfoUseCase): BaseUseCase {

    suspend operator fun invoke(itemName: String): Result<ResponseItemInfoDTO> {
        val requestItemInfo = RequestItemInfoDTO(itemName = itemName)
        return getItemInfoUseCase.invoke(requestItemInfo)
    }
}