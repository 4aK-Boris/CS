package dmitriy.losev.cs.usecases.skins

import dmitriy.losev.cs.dto.skins.SkinsAvailabilityResponseDTO
import dmitriy.losev.cs.repositories.LisSkinsSkinsRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class CheckSkinsAvailabilityForPurchaseUseCase(@Provided private val lisSkinsSkinsRepository: LisSkinsSkinsRepository) : BaseUseCase {

    suspend operator fun invoke(skinIds: List<Int>): Result<SkinsAvailabilityResponseDTO> = runCatching {
        lisSkinsSkinsRepository.checkSkinsAvailabilityForPurchase(skinIds)
    }
}