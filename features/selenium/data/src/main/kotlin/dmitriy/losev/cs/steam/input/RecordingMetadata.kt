package dmitriy.losev.cs.steam.input

import kotlinx.serialization.Serializable

@Serializable
data class RecordingMetadata(
    val recordingStart: String,
    val durationSec: Double,
    val inputHz: Int,
    val totalTicks: Int,
    val totalEvents: Int,
    val roundsPlayed: Int
)