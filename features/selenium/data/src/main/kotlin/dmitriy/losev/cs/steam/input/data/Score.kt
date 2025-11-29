package dmitriy.losev.cs.steam.input.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Score(

    @SerialName(value = "score")
    val score: Int,

    @SerialName(value = "consecutive_round_losses")
    val consecutiveRoundLosses: Int? = null,

    @SerialName(value = "timeouts_remaining")
    val timeoutsRemaining: Int? = null,

    @SerialName(value = "matches_won_this_series")
    val matchesWonThisSeries: Int? = null
)