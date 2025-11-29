package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dso.skins.BuyOrWithdrawSkinForUserResponseDSO
import dmitriy.losev.cs.dto.skins.BuyOrWithdrawSkinForUserResponseDTO
import org.koin.core.annotation.Factory
import kotlin.time.ExperimentalTime

@Factory
@OptIn(ExperimentalTime::class)
class BuyOrWithdrawSkinForUserResponseMapper(
    private val datemTimeMapper: DateTimeMapper,
    private val tradeSkinMapper: TradeSkinMapper
) {

    fun map(value: BuyOrWithdrawSkinForUserResponseDSO): BuyOrWithdrawSkinForUserResponseDTO {
        return BuyOrWithdrawSkinForUserResponseDTO(
            purchaseId = value.buySkinForUserData.purchaseId,
            steamId = value.buySkinForUserData.steamId,
            createdAt = datemTimeMapper.map(value.buySkinForUserData.createdAt),
            customId = value.buySkinForUserData.customId?.toLongOrNull(),
            skins = value.buySkinForUserData.skins.map(tradeSkinMapper::map)
        )
    }
}