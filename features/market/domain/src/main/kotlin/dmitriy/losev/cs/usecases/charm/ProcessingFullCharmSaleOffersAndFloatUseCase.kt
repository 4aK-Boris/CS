package dmitriy.losev.cs.usecases.charm

import dmitriy.losev.cs.dto.charm.CharmInfoDTO
import dmitriy.losev.cs.usecases.AwaitIfQueenSizeIsBigUseCase
import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.throwException
import kotlinx.coroutines.delay
import org.koin.core.annotation.Factory

@Factory
class ProcessingFullCharmSaleOffersAndFloatUseCase(
    private val processingPartOfCharmSaleOffersAndFloatUseCase: ProcessingPartOfCharmSaleOffersAndFloatUseCase,
    private val getCharmSaleOffersCountUseCase: GetCharmSaleOffersCountUseCase,
    private val awaitIfQueenSizeIsBigUseCase: AwaitIfQueenSizeIsBigUseCase
) : BaseUseCase {

    suspend operator fun invoke(charmInfo: CharmInfoDTO): Result<Unit> {
        return getCharmSaleOffersCountUseCase.invoke(name = charmInfo.name).mapCatching { requestCount ->
            delay(5_000L)
            var currentStart = 0
            while (currentStart < requestCount) {
                processingPartOfCharmSaleOffersAndFloatUseCase.invoke(charmInfo = charmInfo, startPosition = currentStart, requestCount = STEP).getOrNull()
                currentStart += STEP
                awaitIfQueenSizeIsBigUseCase.invoke().throwException()
            }
        }
    }

    companion object {
        private const val STEP = 100
    }
}