package dmitriy.losev.cs.usecases

import dmitriy.losev.cs.core.PulseMarketUseCase
import dmitriy.losev.cs.pulse.Market
import dmitriy.losev.cs.usecases.offers.GetOffersWithAuthTokenUseCase
import dmitriy.losev.cs.usecases.pulse.InsertMarketItemsUseCase
import org.koin.core.annotation.Factory

@Factory
class GetAndSaveLisSkinsToCsMarketItemsUseCase(
    getOffersWithAuthTokenUseCase: GetOffersWithAuthTokenUseCase,
    insertMarketItemsUseCase: InsertMarketItemsUseCase
) : BaseUseCase, PulseMarketUseCase(getOffersWithAuthTokenUseCase, insertMarketItemsUseCase) {

    override val buyMarket = Market.LIS_SKINS

    override val sellMarket = Market.CS_MARKET

    override val minProfit = 20
}