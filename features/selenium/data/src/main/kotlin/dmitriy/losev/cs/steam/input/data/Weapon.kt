package dmitriy.losev.cs.steam.input.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Weapon(

    @SerialName(value = "name")
    val name: String,

    @SerialName(value = "paintkit")
    val paintKit: String,

    @SerialName(value = "type")
    val type: String? = null,

    @SerialName(value = "ammo_clip")
    val ammoClip: Int? = null,

    @SerialName(value = "ammo_clip_max")
    val ammoClipMax: Int? = null,

    @SerialName(value = "ammo_reserve")
    val ammoReserve: Int? = null,

    @SerialName(value = "state")
    val state: String
)