package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dso.account.BalanceResponseDSO
import dmitriy.losev.cs.dto.account.BalanceResponseDTO
import org.koin.core.annotation.Factory

@Factory
class BalanceResponseMapper {

    fun map(value: BalanceResponseDSO): BalanceResponseDTO {
        return BalanceResponseDTO(balance = value.data.balance)
    }
}