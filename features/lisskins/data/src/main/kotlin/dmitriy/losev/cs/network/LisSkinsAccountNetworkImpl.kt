package dmitriy.losev.cs.network

import dmitriy.losev.cs.clients.LisSkinsNetworkClient
import dmitriy.losev.cs.dso.account.BalanceResponseDSO
import org.koin.core.annotation.Factory

@Factory(binds = [LisSkinsAccountNetwork::class])
class LisSkinsAccountNetworkImpl(private val lisSkinsNetworkClient: LisSkinsNetworkClient): LisSkinsAccountNetwork {

    override suspend fun getUserBalance(): BalanceResponseDSO {
        return lisSkinsNetworkClient.get(
            handle = GET_BALANCE_HANDLE,
            responseClazz = BalanceResponseDSO::class
        )
    }

    companion object {
        private const val GET_BALANCE_HANDLE = "/v1/user/balance"
    }
}