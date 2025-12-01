package dmitriy.losev.cs.usecases.pulse

//import dmitriy.losev.cs.dto.pulse.MarketItemDTO
//import dmitriy.losev.cs.pulse.Market
//import dmitriy.losev.cs.repositories.pulse.MarketTradeRepository
//import dmitriy.losev.cs.usecases.BaseUseCase
//import org.koin.core.annotation.Factory
//import org.koin.core.annotation.Provided
//
//@Factory
//class GetMarketItemsUseCase(@Provided private val marketTradeRepository: MarketTradeRepository): BaseUseCase {
//
//    suspend operator fun invoke(buyMarket: Market, sellMarket: Market): Result<List<MarketItemDTO>> = runCatching {
//        marketTradeRepository.getMarkerItems(buyMarket, sellMarket)
//    }
//}
