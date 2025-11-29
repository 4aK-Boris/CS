package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dso.skins.BuySkinForUserRequestDSO
import dmitriy.losev.cs.dto.skins.BuySkinForUserRequestDTO
import org.koin.core.annotation.Factory

@Factory
class BuySkinForUserRequestMapper {

    fun map(value: BuySkinForUserRequestDTO): BuySkinForUserRequestDSO {
        return BuySkinForUserRequestDSO(
            ids = value.ids,
            partner = value.partner,
            token = value.token,
            maxPrice = value.maxPrice,
            customId = value.customId.toString(),
            skipUnavailable = value.skipUnavailable
        )
    }
}