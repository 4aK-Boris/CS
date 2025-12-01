package dmitriy.losev.cs.dto

import dmitriy.losev.cs.core.ConfirmationType

data class ConfirmationDTO(
    val id: String,
    val key: String,
    val type: ConfirmationType,
    val creatorId: String?
)
