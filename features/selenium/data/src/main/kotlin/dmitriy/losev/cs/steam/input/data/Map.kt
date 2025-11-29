package dmitriy.losev.cs.steam.input.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Map(

    @SerialName(value = "mode")
    val mode: String,

    @SerialName(value = "name")
    val name: String,

    @SerialName(value = "round")
    val round: Int,

    @SerialName(value = "team_ct")
    val teamCounterTerrorist: Score,

    @SerialName(value = "team_t")
    val teamTerrorist: Score,

    @SerialName(value = "num_matches_to_win_series")
    val numMatchesToWinSeries: Int? = null
)
