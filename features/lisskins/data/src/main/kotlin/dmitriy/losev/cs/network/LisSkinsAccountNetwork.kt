package dmitriy.losev.cs.network

import dmitriy.losev.cs.dso.account.BalanceResponseDSO

interface LisSkinsAccountNetwork {

    suspend fun getUserBalance(): BalanceResponseDSO
}