package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dto.StripeConnectDTO
import org.koin.core.annotation.Factory

@Factory
internal class StripeConnectMapper {

    fun map(): StripeConnectDTO {
        return StripeConnectDTO()
    }
}
