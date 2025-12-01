package dmitriy.losev.cs.network

import dmitriy.losev.cs.dso.skins.BuyOrWithdrawSkinForUserResponseDSO
import dmitriy.losev.cs.dso.skins.BuySkinForUserRequestDSO
import dmitriy.losev.cs.dso.skins.SearchSkinsRequestDSO
import dmitriy.losev.cs.dso.skins.SearchSkinsResponseDSO
import dmitriy.losev.cs.dso.skins.SkinsAvailabilityResponseDSO
import dmitriy.losev.cs.dso.skins.WithdrawSkinsRequestDSO

interface LisSkinsSkinsNetwork {

    suspend fun searchSkins(searchSkinsRequest: SearchSkinsRequestDSO): SearchSkinsResponseDSO

    suspend fun checkSkinsAvailabilityForPurchase(skinIds: List<Int>): SkinsAvailabilityResponseDSO

    suspend fun buySkinsForUser(buySkinForUserRequestDSO: BuySkinForUserRequestDSO): BuyOrWithdrawSkinForUserResponseDSO

    suspend fun withdrawSkinsForUser(withdrawSkinsRequest: WithdrawSkinsRequestDSO): BuyOrWithdrawSkinForUserResponseDSO
}