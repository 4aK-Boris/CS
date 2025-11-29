package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.dto.skins.BuyOrWithdrawSkinForUserResponseDTO
import dmitriy.losev.cs.dto.skins.BuySkinForUserRequestDTO
import dmitriy.losev.cs.dto.skins.SearchSkinsRequestDTO
import dmitriy.losev.cs.dto.skins.SearchSkinsResponseDTO
import dmitriy.losev.cs.dto.skins.SkinsAvailabilityResponseDTO
import dmitriy.losev.cs.dto.skins.WithdrawSkinsRequestDTO
import dmitriy.losev.cs.mappers.BuyOrWithdrawSkinForUserResponseMapper
import dmitriy.losev.cs.mappers.BuySkinForUserRequestMapper
import dmitriy.losev.cs.mappers.SearchSkinsRequestMapper
import dmitriy.losev.cs.mappers.SearchSkinsResponseMapper
import dmitriy.losev.cs.mappers.SkinsAvailabilityResponseMapper
import dmitriy.losev.cs.mappers.WithdrawSkinsRequestMapper
import dmitriy.losev.cs.network.LisSkinsSkinsNetwork
import org.koin.core.annotation.Factory

@Factory(binds = [LisSkinsSkinsRepository::class])
class LisSkinsSkinsRepositoryImpl(
    private val lisSkinsSkinsNetwork: LisSkinsSkinsNetwork,
    private val searchSkinsRequestMapper: SearchSkinsRequestMapper,
    private val searchSkinsResponseMapper: SearchSkinsResponseMapper,
    private val skinsAvailabilityResponseMapper: SkinsAvailabilityResponseMapper,
    private val buySkinForUserRequestMapper: BuySkinForUserRequestMapper,
    private val withdrawSkinsRequestMapper: WithdrawSkinsRequestMapper,
    private val buyOrWithdrawSkinForUserResponseMapper: BuyOrWithdrawSkinForUserResponseMapper
) : LisSkinsSkinsRepository {

    override suspend fun searchSkins(searchSkinsRequest: SearchSkinsRequestDTO): SearchSkinsResponseDTO {
        return searchSkinsResponseMapper.map(value = lisSkinsSkinsNetwork.searchSkins(searchSkinsRequest = searchSkinsRequestMapper.map(value = searchSkinsRequest)))
    }

    override suspend fun checkSkinsAvailabilityForPurchase(skinIds: List<Int>): SkinsAvailabilityResponseDTO {
        return skinsAvailabilityResponseMapper.map(value = lisSkinsSkinsNetwork.checkSkinsAvailabilityForPurchase(skinIds))
    }

    override suspend fun buySkinsForUser(buySkinForUserRequest: BuySkinForUserRequestDTO): BuyOrWithdrawSkinForUserResponseDTO {
        return buyOrWithdrawSkinForUserResponseMapper.map(value = lisSkinsSkinsNetwork.buySkinsForUser(buySkinForUserRequestDSO = buySkinForUserRequestMapper.map(value = buySkinForUserRequest)))
    }

    override suspend fun withdrawSkinsForUser(withdrawSkinsRequest: WithdrawSkinsRequestDTO): BuyOrWithdrawSkinForUserResponseDTO {
        return buyOrWithdrawSkinForUserResponseMapper.map(value = lisSkinsSkinsNetwork.withdrawSkinsForUser(withdrawSkinsRequest = withdrawSkinsRequestMapper.map(value = withdrawSkinsRequest)))
    }
}