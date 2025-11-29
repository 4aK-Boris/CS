package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dso.skins.WithdrawSkinsRequestDSO
import dmitriy.losev.cs.dto.skins.WithdrawSkinsRequestDTO
import org.koin.core.annotation.Factory

@Factory
class WithdrawSkinsRequestMapper {

    fun map(value: WithdrawSkinsRequestDTO): WithdrawSkinsRequestDSO {
        return WithdrawSkinsRequestDSO(
            customId = value.customId.toString(),
            purchaseId = value.purchaseId,
            partner = value.partner,
            token = value.token
        )
    }
}