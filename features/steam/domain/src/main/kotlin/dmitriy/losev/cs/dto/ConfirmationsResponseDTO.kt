package dmitriy.losev.cs.dto

data class ConfirmationsResponseDTO(
    val success: Boolean,
    val confirmations: List<ConfirmationDTO>?
)
