package dmitriy.losev.cs.dto

data class TradeOfferAcceptResultDTO(
    val tradeId: String?,
    val needsMobileConfirmation: Boolean,
    val strError: String?
)