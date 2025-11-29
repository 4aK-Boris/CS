package dmitriy.losev.cs.dto.auth

data class AuthResponseDTO(val authData: AuthDataDTO, val twoFactorData: String?, val isEmailConfirmed: Boolean)