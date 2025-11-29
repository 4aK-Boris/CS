package dmitriy.losev.cs.steam.input.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonCSData(

    @SerialName(value = "provider")
    val provider: Provider,

    @SerialName(value = "map")
    val map: Map? = null,

    @SerialName(value = "round")
    val round: Round? = null,

    @SerialName(value = "player")
    val player: Player? = null
)