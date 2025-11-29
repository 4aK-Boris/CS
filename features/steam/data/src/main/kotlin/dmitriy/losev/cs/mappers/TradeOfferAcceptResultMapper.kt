package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dso.TradeOfferAcceptResultDSO
import dmitriy.losev.cs.dto.TradeOfferAcceptResultDTO
import org.koin.core.annotation.Factory

@Factory
class TradeOfferAcceptResultMapper {

    fun map(value: TradeOfferAcceptResultDSO): TradeOfferAcceptResultDTO {
        return TradeOfferAcceptResultDTO(
            tradeId = value.tradeId,
            needsMobileConfirmation = value.needsMobileConfirmation,
            strError = value.strError
        )
    }
}