package dmitriy.losev.cs.network

import dmitriy.losev.cs.clients.LisSkinsNetworkClient
import dmitriy.losev.cs.dso.skins.BuyOrWithdrawSkinForUserResponseDSO
import dmitriy.losev.cs.dso.skins.BuySkinForUserRequestDSO
import dmitriy.losev.cs.dso.skins.SearchSkinsRequestDSO
import dmitriy.losev.cs.dso.skins.SearchSkinsResponseDSO
import dmitriy.losev.cs.dso.skins.SkinsAvailabilityResponseDSO
import dmitriy.losev.cs.dso.skins.WithdrawSkinsRequestDSO
import org.koin.core.annotation.Factory

@Factory(binds = [LisSkinsSkinsNetwork::class])
class LisSkinsSkinsNetworkImpl(private val lisSkinsNetworkClient: LisSkinsNetworkClient): LisSkinsSkinsNetwork {

    override suspend fun searchSkins(searchSkinsRequest: SearchSkinsRequestDSO): SearchSkinsResponseDSO {
        return lisSkinsNetworkClient.get(
            handle = SEARCH_SKINS_HANDLE,
            responseClazz = SearchSkinsResponseDSO::class,
            params = searchSkinsRequest.convertToParameters()
        )
    }

    override suspend fun checkSkinsAvailabilityForPurchase(skinIds: List<Int>): SkinsAvailabilityResponseDSO {
        return lisSkinsNetworkClient.get(
            handle = CHECK_SKINS_AVAILABILITY_FOR_PURCHASE_HANDLE,
            responseClazz = SkinsAvailabilityResponseDSO::class,
            params = skinIds.mapIndexed { index, skinId -> "ids[$index]" to skinId.toString() }.toMap()
        )
    }

    override suspend fun buySkinsForUser(buySkinForUserRequestDSO: BuySkinForUserRequestDSO): BuyOrWithdrawSkinForUserResponseDSO {
        return lisSkinsNetworkClient.post(
            handle = BUY_SKINS_FOR_USER_HANDLE,
            requestClazz = BuySkinForUserRequestDSO::class,
            responseClazz = BuyOrWithdrawSkinForUserResponseDSO::class,
            body = buySkinForUserRequestDSO
        )
    }

    override suspend fun withdrawSkinsForUser(withdrawSkinsRequest: WithdrawSkinsRequestDSO): BuyOrWithdrawSkinForUserResponseDSO {
        return lisSkinsNetworkClient.post(
            handle = WITHDRAW_SKINS_FOR_USER_HANDLE,
            requestClazz = WithdrawSkinsRequestDSO::class,
            responseClazz = BuyOrWithdrawSkinForUserResponseDSO::class,
            body = withdrawSkinsRequest
        )
    }

    companion object {
        private const val SEARCH_SKINS_HANDLE = "/v1/market/search"
        private const val CHECK_SKINS_AVAILABILITY_FOR_PURCHASE_HANDLE = "/v1/market/check-availability"
        private const val BUY_SKINS_FOR_USER_HANDLE = "/v1/market/buy"
        private const val WITHDRAW_SKINS_FOR_USER_HANDLE = "/v1/market/withdraw"
    }
}