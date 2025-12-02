package dmitriy.losev.cs.models

import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Serializable
@OptIn(ExperimentalTime::class)
data class GetSteamAccountResponseModel(
    val steamId: String,
    val login: String,
    val password: String,
    val sharedSecret: String,
    val identitySecret: String,
    val revocationCode: String,
    val deviceId: String,
    val createdAt: Instant
)
