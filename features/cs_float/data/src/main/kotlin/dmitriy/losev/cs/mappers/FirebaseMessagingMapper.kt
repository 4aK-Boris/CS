package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dso.FirebaseMessagingDSO
import dmitriy.losev.cs.dto.FirebaseMessagingDTO
import org.koin.core.annotation.Factory

@Factory
internal class FirebaseMessagingMapper {

    fun map(value: FirebaseMessagingDSO): FirebaseMessagingDTO {
        return FirebaseMessagingDTO(
            lastUpdated = value.lastUpdated,
            platform = value.platform
        )
    }
}
