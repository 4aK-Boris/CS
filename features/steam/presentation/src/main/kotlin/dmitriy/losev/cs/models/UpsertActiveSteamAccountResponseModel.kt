package dmitriy.losev.cs.models

import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Serializable
@OptIn(ExperimentalTime::class)
data class UpsertActiveSteamAccountResponseModel(
    val steamId: String,
    val login: String,
    val createdAt: Instant? = null
)
