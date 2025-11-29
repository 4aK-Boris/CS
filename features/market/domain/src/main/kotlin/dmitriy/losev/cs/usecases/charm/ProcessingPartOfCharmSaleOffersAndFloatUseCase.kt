package dmitriy.losev.cs.usecases.charm

import dmitriy.losev.cs.dto.charm.CharmInfoDTO
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory

@Factory
class ProcessingPartOfCharmSaleOffersAndFloatUseCase(
    private val getAndSaveCharmSaleOffersUseCase: GetAndSaveCharmSaleOffersUseCase,
    private val getAndSaveCharmsFloatUseCase: GetAndSaveCharmsFloatUseCase
): BaseUseCase {

    suspend operator fun invoke(charmInfo: CharmInfoDTO, startPosition: Int, requestCount: Int = 100): Result<Unit> {
        return getAndSaveCharmSaleOffersUseCase.invoke(charmInfo, startPosition, requestCount).mapCatching{ charmsLink ->
            getAndSaveCharmsFloatUseCase.invoke(charmsLink).getOrThrow()
        }
    }
}