package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dso.CsFloatAccountDSO
import dmitriy.losev.cs.dto.CsFloatAccountDTO
import org.koin.core.annotation.Factory

@Factory
internal class CsFloatAccountMapper(private val userMapper: UserMapper) {

    fun map(value: CsFloatAccountDSO): CsFloatAccountDTO {
        return CsFloatAccountDTO(
            actionableTrades = value.actionableTrades,
            hasUnreadNotifications = value.hasUnreadNotifications,
            pendingOffers = value.pendingOffers,
            user = userMapper.map(value = value.user)
        )
    }
}
