package dmitriy.losev.cs.steam.input.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Player(

    @SerialName(value = "steamid")
    val steamId: String,

    @SerialName(value = "name")
    val name: String,

    @SerialName(value = "team")
    val team: String? = null,

    @SerialName(value = "state")
    val state: State? = null,

    @SerialName(value = "weapons")
    val weapons: Weapons? = null,
)
