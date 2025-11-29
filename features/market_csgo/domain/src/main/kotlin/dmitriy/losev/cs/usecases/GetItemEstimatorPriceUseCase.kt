package dmitriy.losev.cs.usecases

import dmitriy.losev.cs.core.PriceEstimator
import dmitriy.losev.cs.dto.sales.ItemHistorySalesDTO
import dmitriy.losev.cs.repositories.MarketCSGOSalesRepository
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetItemEstimatorPriceUseCase(
    private val priceEstimator: PriceEstimator,
    private val getItemHistorySalesUseCase: GetItemHistorySalesUseCase
) : BaseUseCase {

    suspend operator fun invoke(steamId: ULong, itemName: String): Result<Double> {
        return getItemHistorySalesUseCase.invoke(steamId = steamId, itemId = 1).mapCatching { itemHistorySales ->
            priceEstimator.estimate(sales = itemHistorySales.historySales, salesPerWeek = itemHistorySales.salesCount7)?.price.let { estimatorPrice ->
                requireNotNull(value = estimatorPrice) { "Estimator price cannot be null" }
            }
        }
    }
}