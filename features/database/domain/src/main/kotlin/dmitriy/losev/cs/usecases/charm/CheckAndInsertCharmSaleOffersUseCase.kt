package dmitriy.losev.cs.usecases.charm

import dmitriy.losev.cs.dto.ListingInfoDTO
import dmitriy.losev.cs.dto.charm.CharmLinkDTO
import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.mapCatchingInData
import org.koin.core.annotation.Factory

@Factory
class CheckAndInsertCharmSaleOffersUseCase(
    private val checkCharmSaleOffersUseCase: CheckCharmSaleOffersUseCase,
    private val insertCharmSaleOffersUseCase: InsertCharmSaleOffersUseCase
): BaseUseCase {

    suspend operator fun invoke(charms: List<Triple<ULong, ULong, ListingInfoDTO>>): Result<List<CharmLinkDTO>> {
        return checkCharmSaleOffersUseCase.invoke(charms).mapCatchingInData { charmSaleOffers ->
            insertCharmSaleOffersUseCase.invoke(charmSaleOffers)
        }
    }
}