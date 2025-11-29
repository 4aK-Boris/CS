package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.dto.account.BalanceResponseDTO

interface LisSkinsAccountRepository {

    suspend fun getUserBalance(): BalanceResponseDTO
}