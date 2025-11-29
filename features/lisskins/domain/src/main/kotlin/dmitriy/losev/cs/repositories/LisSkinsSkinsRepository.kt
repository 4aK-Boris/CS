package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.dto.skins.BuyOrWithdrawSkinForUserResponseDTO
import dmitriy.losev.cs.dto.skins.BuySkinForUserRequestDTO
import dmitriy.losev.cs.dto.skins.SearchSkinsRequestDTO
import dmitriy.losev.cs.dto.skins.SearchSkinsResponseDTO
import dmitriy.losev.cs.dto.skins.SkinsAvailabilityResponseDTO
import dmitriy.losev.cs.dto.skins.WithdrawSkinsRequestDTO

interface LisSkinsSkinsRepository {

    suspend fun searchSkins(searchSkinsRequest: SearchSkinsRequestDTO): SearchSkinsResponseDTO

    suspend fun checkSkinsAvailabilityForPurchase(skinIds: List<Int>): SkinsAvailabilityResponseDTO

    suspend fun buySkinsForUser(buySkinForUserRequest: BuySkinForUserRequestDTO): BuyOrWithdrawSkinForUserResponseDTO

    suspend fun withdrawSkinsForUser(withdrawSkinsRequest: WithdrawSkinsRequestDTO): BuyOrWithdrawSkinForUserResponseDTO
}