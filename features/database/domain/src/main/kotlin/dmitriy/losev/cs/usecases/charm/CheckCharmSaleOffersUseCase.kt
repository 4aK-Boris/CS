package dmitriy.losev.cs.usecases.charm

import dmitriy.losev.cs.dto.ListingInfoDTO
import dmitriy.losev.cs.dto.charm.CharmSaleOfferDTO
import dmitriy.losev.cs.repositories.DatabaseCharmRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class CheckCharmSaleOffersUseCase(@Provided private val databaseCharmRepository: DatabaseCharmRepository): BaseUseCase {

    suspend operator fun invoke(charms: List<Triple<ULong, ULong, ListingInfoDTO>>): Result<List<CharmSaleOfferDTO>> = runCatching {
        databaseCharmRepository.checkCharmSaleOffers(charms)
    }
}