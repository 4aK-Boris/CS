package dmitriy.losev.cs.usecases.charm

import dmitriy.losev.cs.dto.charm.CharmLinkDTO
import dmitriy.losev.cs.dto.charm.CharmSaleOfferDTO
import dmitriy.losev.cs.repositories.DatabaseCharmRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class InsertCharmSaleOffersUseCase(@Provided private val databaseCharmRepository: DatabaseCharmRepository): BaseUseCase {

    suspend operator fun invoke(charmSaleOffers: List<CharmSaleOfferDTO>): Result<List<CharmLinkDTO>> = runCatching {
        databaseCharmRepository.insertCharmSaleOffers(charmSaleOffers)
    }
}