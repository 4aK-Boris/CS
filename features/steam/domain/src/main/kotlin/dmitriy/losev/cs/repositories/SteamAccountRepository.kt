package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.dto.ConfirmationDTO
import dmitriy.losev.cs.dto.ConfirmationsResponseDTO
import dmitriy.losev.cs.dto.RSAKeyDTO
import dmitriy.losev.cs.dto.TradeOfferAcceptResultDTO
import dmitriy.losev.cs.dto.TradeOfferInfoDTO
import dmitriy.losev.cs.dto.steam.SteamAccountCookiesDTO

interface SteamAccountRepository {

    suspend fun getConfirmations(steamId: ULong, deviceId: String, confirmationKey: String): ConfirmationsResponseDTO

    suspend fun confirmTrade(steamId: ULong, deviceId: String, confirmationKey: String, confirmation: ConfirmationDTO): Boolean

    suspend fun getRSAKey(steamId: ULong, login: String): RSAKeyDTO

    suspend fun login(steamId: ULong, login: String, encryptedPassword: String, steamGuardCode: String, rsaKeyTimeStamp: Long)

    suspend fun getTradeOffers(steamId: ULong): List<TradeOfferInfoDTO>

    suspend fun acceptTradeOffer(steamId: ULong, tradeOfferInfo: TradeOfferInfoDTO): TradeOfferAcceptResultDTO
}