package dmitriy.losev.cs.usecases.charm

import dmitriy.losev.cs.dto.charm.CharmInfoDTO
import dmitriy.losev.cs.dto.charm.CharmLinkDTO
import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.mapCatchingInData
import org.koin.core.annotation.Factory

@Factory
class GetAndSaveCharmSaleOffersUseCase(
    private val getCharmSaleOffersUseCase: GetCharmSaleOffersUseCase,
    private val checkAndInsertCharmSaleOffersUseCase: CheckAndInsertCharmSaleOffersUseCase
) : BaseUseCase {

    suspend operator fun invoke(charmInfo: CharmInfoDTO, startPosition: Int, requestCount: Int = 100): Result<List<CharmLinkDTO>> {
        return getCharmSaleOffersUseCase.invoke(name = charmInfo.name, startPosition = startPosition, requestCount = requestCount).mapCatchingInData { listingsInfo ->
            val charms = listingsInfo.map { listingInfo -> Triple(first = charmInfo.classId, second = charmInfo.instanceId, third = listingInfo) }
            checkAndInsertCharmSaleOffersUseCase.invoke(charms = charms)
        }
    }
}