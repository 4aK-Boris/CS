package dmitriy.losev.cs.dto.skins

data class WithdrawSkinsRequestDTO(
    val customId: Long,
    val purchaseId: Int,
    val partner: String,
    val token: String
)