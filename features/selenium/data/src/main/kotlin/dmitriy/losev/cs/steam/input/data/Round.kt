package dmitriy.losev.cs.steam.input.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Round(

    @SerialName(value = "phase")
    val phase: String,

    @SerialName(value = "bomb")
    val bomb: String? = null,

    @SerialName(value = "win_team")
    val winnerTeam: String? = null
)
