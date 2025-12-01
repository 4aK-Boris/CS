package dmitriy.losev.cs.models

import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Serializable
@OptIn(ExperimentalTime::class)
data class UpsertSteamAccountResponseModel(
    val steamId: String,
    val login: String,
    val createdAt: Instant? = null
)
