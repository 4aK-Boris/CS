package dmitriy.losev.cs.models

import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Serializable
@OptIn(ExperimentalTime::class)
data class GetActiveSteamAccountResponseModel(
    val steamId: String,
    val marketCSGOApiToken: String,
    val accessToken: String,
    val refreshToken: String,
    val sessionId: String,
    val createdAt: Instant
)
