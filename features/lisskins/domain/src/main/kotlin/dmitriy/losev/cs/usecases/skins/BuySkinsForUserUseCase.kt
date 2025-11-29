package dmitriy.losev.cs.usecases.skins

import dmitriy.losev.cs.dto.skins.BuyOrWithdrawSkinForUserResponseDTO
import dmitriy.losev.cs.dto.skins.BuySkinForUserRequestDTO
import dmitriy.losev.cs.repositories.LisSkinsSkinsRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class BuySkinsForUserUseCase(@Provided private val lisSkinsSkinsRepository: LisSkinsSkinsRepository) : BaseUseCase {

    suspend operator fun invoke(buySkinForUserRequest: BuySkinForUserRequestDTO): Result<BuyOrWithdrawSkinForUserResponseDTO> = runCatching {
        lisSkinsSkinsRepository.buySkinsForUser(buySkinForUserRequest)
    }
}