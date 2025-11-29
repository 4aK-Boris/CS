package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.dto.account.BalanceResponseDTO
import dmitriy.losev.cs.mappers.BalanceResponseMapper
import dmitriy.losev.cs.network.LisSkinsAccountNetwork
import org.koin.core.annotation.Factory

@Factory(binds = [LisSkinsAccountRepository::class])
class LisSkinsAccountRepositoryImpl(
    private val lisSkinsAccountNetwork: LisSkinsAccountNetwork,
    private val balanceResponseMapper: BalanceResponseMapper
): LisSkinsAccountRepository {

    override suspend fun getUserBalance(): BalanceResponseDTO {
        return balanceResponseMapper.map(value = lisSkinsAccountNetwork.getUserBalance())
    }
}