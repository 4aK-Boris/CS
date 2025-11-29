package dmitriy.losev.cs.steam.input.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Provider(

    @SerialName(value = "appid")
    val appId: Int,

    @SerialName(value = "version")
    val version: Int,

    @SerialName(value = "steamid")
    val steamId: Long,

    @SerialName(value = "timestamp")
    val dateTime: Long
)