package dmitriy.losev.cs.dto.auth

data class BeginAuthSessionViaCredentialsResponseDTO(
    val clientId: String,
    val requestId: String,
    val interval: Int,
    val allowedConfirmations: List<ConfirmationTypeDTO>,
    val steamId: Long,
    val weakToken: String,
    val extendedErrorMessage: String
)
