package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dso.TradeOfferInfoDSO
import dmitriy.losev.cs.dto.TradeOfferInfoDTO
import org.jsoup.Jsoup
import org.koin.core.annotation.Factory

@Factory
class TradeOfferInfoMapper {

    fun map(value: String): List<TradeOfferInfoDTO> {
        val doc = Jsoup.parse(value)
        return doc.select(".tradeoffer").mapNotNull { element ->
            val offerId = element.attr("id").removePrefix("tradeofferid_")
            val partnerId = element.attr("data-partnerid")
            val partnerName = element.selectFirst(".tradeoffer_partner a")?.text() ?: ""
            if (offerId.isNotEmpty() && partnerId.isNotEmpty()) {
                TradeOfferInfoDTO(offerId, partnerId, partnerName)
            } else {
                null
            }
        }
    }

    fun map(value: TradeOfferInfoDTO): TradeOfferInfoDSO {
        return TradeOfferInfoDSO(
            offerId = value.offerId,
            partnerId = value.partnerId
        )
    }
}