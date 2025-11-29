package dmitriy.losev.cs.usecases.account

import dmitriy.losev.cs.dto.TradeOfferInfoDTO
import dmitriy.losev.cs.dto.steam.SteamAccountCookiesDTO
import dmitriy.losev.cs.repositories.SteamAccountRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetTradeOffersUseCase(@Provided private val steamAccountRepository: SteamAccountRepository) : BaseUseCase {

    suspend operator fun invoke(steamId: ULong, cookies: SteamAccountCookiesDTO): Result<List<TradeOfferInfoDTO>> = runCatching {
        steamAccountRepository.getTradeOffers(steamId, cookies)
    }
}