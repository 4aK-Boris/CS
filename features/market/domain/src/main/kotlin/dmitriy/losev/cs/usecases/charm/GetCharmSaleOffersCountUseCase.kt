package dmitriy.losev.cs.usecases.charm

import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.GetItemSaleOffersCountUseCase
import org.koin.core.annotation.Factory

@Factory
class GetCharmSaleOffersCountUseCase(private val getItemSaleOffersCountUseCase: GetItemSaleOffersCountUseCase): BaseUseCase {

    suspend operator fun invoke(name: String): Result<Int> {
        return getItemSaleOffersCountUseCase.invoke(itemName = name)
    }
}