package dmitriy.losev.cs.tasks

import dmitriy.losev.cs.usecases.GetAndSaveLisSkinsToCsMarketItemsUseCase
import dmitriy.losev.cs.usecases.pulse.RemoveOldMarketItemsUseCase
import org.koin.core.annotation.Factory

@Factory
class LisSkinsToCsMarketTradeTasks(
    private val getAndSaveLisSkinsToCsMarketItemsUseCase: GetAndSaveLisSkinsToCsMarketItemsUseCase,
    private val removeOldLisSkinsToCsMarketItemsUseCase: RemoveOldMarketItemsUseCase
) {

    suspend fun startGetAndSaveLisSkinsToCsMarketItemsTask() {
        getAndSaveLisSkinsToCsMarketItemsUseCase.invoke().getOrThrow()
    }

    suspend fun startRemoveOldLisSkinsToCsMarketItemsTask() {
        removeOldLisSkinsToCsMarketItemsUseCase.invoke().getOrThrow()
    }
}