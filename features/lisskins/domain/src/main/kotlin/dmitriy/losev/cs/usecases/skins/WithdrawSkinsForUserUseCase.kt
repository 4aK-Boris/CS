package dmitriy.losev.cs.usecases.skins

import dmitriy.losev.cs.dto.skins.BuyOrWithdrawSkinForUserResponseDTO
import dmitriy.losev.cs.dto.skins.WithdrawSkinsRequestDTO
import dmitriy.losev.cs.repositories.LisSkinsSkinsRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class WithdrawSkinsForUserUseCase(@Provided private val lisSkinsSkinsRepository: LisSkinsSkinsRepository) : BaseUseCase {

    suspend operator fun invoke(withdrawSkinsRequest: WithdrawSkinsRequestDTO): Result<BuyOrWithdrawSkinForUserResponseDTO> = runCatching {
        lisSkinsSkinsRepository.withdrawSkinsForUser(withdrawSkinsRequest)
    }
}