package dmitriy.losev.cs.usecases.account

import dmitriy.losev.cs.dto.TradeOfferAcceptResultDTO
import dmitriy.losev.cs.dto.TradeOfferInfoDTO
import dmitriy.losev.cs.repositories.SteamAccountRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class AcceptTradeOfferUseCase(@Provided private val steamAccountRepository: SteamAccountRepository): BaseUseCase {

    suspend operator fun invoke(steamId: ULong, steamAccountCookies: String, tradeOfferInfo: TradeOfferInfoDTO): Result<TradeOfferAcceptResultDTO> = runCatching {
        steamAccountRepository.acceptTradeOffer(steamId, steamAccountCookies, tradeOfferInfo)
    }
}