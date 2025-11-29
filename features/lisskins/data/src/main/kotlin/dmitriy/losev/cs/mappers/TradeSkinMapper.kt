package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dso.skins.TradeSkinDSO
import dmitriy.losev.cs.dto.skins.TradeSkinDTO
import org.koin.core.annotation.Factory
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Factory
class TradeSkinMapper(private val dateTimeMapper: DateTimeMapper) {

    fun map(value: TradeSkinDSO): TradeSkinDTO {
        return TradeSkinDTO(
            id = value.id,
            name = value.name,
            price = value.price,
            status = value.status,
            returnReason = value.returnReason,
            returnChargedCommission = value.returnChargedCommission,
            error = value.error,
            steamTradeOfferId = value.steamTradeOfferId,
            steamTradeOfferCreatedAt = value.steamTradeOfferCreatedAt?.run(block = dateTimeMapper::map),
            steamTradeOfferExpiryAt = value.steamTradeOfferExpiryAt?.run(block = dateTimeMapper::map),
            steamTradeOfferFinishedAt = value.steamTradeOfferFinishedAt?.run(block = dateTimeMapper::map)
        )
    }
}