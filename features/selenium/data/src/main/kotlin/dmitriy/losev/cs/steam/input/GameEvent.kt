package dmitriy.losev.cs.steam.input

import kotlinx.serialization.Serializable

@Serializable
data class GameEvent(
    val type: String,
    val timestamp: Double,
    val round: Int,
    val data: Map<String, String> = emptyMap()
)