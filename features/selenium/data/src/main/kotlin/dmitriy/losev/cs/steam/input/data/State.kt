package dmitriy.losev.cs.steam.input.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class State(

    @SerialName(value = "health")
    val health: Int,

    @SerialName(value = "armor")
    val armor: Int,

    @SerialName(value = "helmet")
    val helmet: Boolean,

    @SerialName(value = "flashed")
    val flashed: Int,

    @SerialName(value = "smoked")
    val smoked: Int,

    @SerialName(value = "burning")
    val burning: Int,

    @SerialName(value = "money")
    val money: Int,

    @SerialName(value = "round_kills")
    val roundKills: Int,

    @SerialName(value = "round_killhs")
    val roundKillsInHeadshot: Int,

    @SerialName(value = "equip_value")
    val equipmentValue: Int
)
