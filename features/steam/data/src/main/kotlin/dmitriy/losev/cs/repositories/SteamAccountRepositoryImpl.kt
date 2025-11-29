package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.dto.ConfirmationDTO
import dmitriy.losev.cs.dto.ConfirmationsResponseDTO
import dmitriy.losev.cs.dto.RSAKeyDTO
import dmitriy.losev.cs.dto.TradeOfferAcceptResultDTO
import dmitriy.losev.cs.dto.TradeOfferInfoDTO
import dmitriy.losev.cs.dto.steam.SteamAccountCookiesDTO
import dmitriy.losev.cs.mappers.ConfirmationMapper
import dmitriy.losev.cs.mappers.ConfirmationsResponseMapper
import dmitriy.losev.cs.mappers.RSAKeyMapper
import dmitriy.losev.cs.mappers.TradeOfferInfoMapper
import dmitriy.losev.cs.network.SteamMobileNetwork
import dmitriy.losev.cs.repositories.steam.SteamDatabaseAccountRepository
import org.koin.core.annotation.Factory

@Factory(binds = [SteamAccountRepository::class])
internal class SteamAccountRepositoryImpl(
    private val steamMobileNetwork: SteamMobileNetwork,
    private val confirmationMapper: ConfirmationMapper,
    private val confirmationsResponseMapper: ConfirmationsResponseMapper,
    private val rsaKeyMapper: RSAKeyMapper,
    private val tradeOfferInfoMapper: TradeOfferInfoMapper,
): SteamAccountRepository {

    override suspend fun getConfirmations(steamId: ULong, deviceId: String, confirmationKey: String): ConfirmationsResponseDTO {
        return confirmationsResponseMapper.map(value = steamMobileNetwork.getConfirmations(steamId, deviceId, confirmationKey))
    }

    override suspend fun confirmTrade(steamId: ULong, deviceId: String, confirmationKey: String, confirmation: ConfirmationDTO): Boolean {
        return steamMobileNetwork.confirmTrade(steamId, deviceId, confirmationKey, confirmationMapper.map(value = confirmation))
    }

    override suspend fun getRSAKey(steamId: ULong, login: String): RSAKeyDTO {
        return rsaKeyMapper.map(value = steamMobileNetwork.getRSAKey(steamId, login))
    }

    override suspend fun login(steamId: ULong, login: String, encryptedPassword: String, steamGuardCode: String, rsaKeyTimeStamp: Long) {
        return steamMobileNetwork.login(steamId, login, encryptedPassword, steamGuardCode, rsaKeyTimeStamp)
    }

    override suspend fun getTradeOffers(steamId: ULong): List<TradeOfferInfoDTO> {
        return tradeOfferInfoMapper.map(value = steamMobileNetwork.getTradeOffers(steamId = steamId))
    }

    override suspend fun acceptTradeOffer(steamId: ULong, steamAccountCookies: String, tradeOfferInfo: TradeOfferInfoDTO): TradeOfferAcceptResultDTO {
        TODO("Not yet implemented")
    }
}