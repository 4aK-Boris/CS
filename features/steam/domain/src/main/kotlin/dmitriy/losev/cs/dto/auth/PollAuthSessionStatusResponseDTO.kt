package dmitriy.losev.cs.dto.auth

data class PollAuthSessionStatusResponseDTO(
    val refreshToken: String,
    val accessToken: String,
    val hadRemoteInteraction: Boolean,
    val login: String,
)
