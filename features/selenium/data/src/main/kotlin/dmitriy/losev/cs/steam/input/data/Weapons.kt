package dmitriy.losev.cs.steam.input.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Weapons(

    @SerialName(value = "weapon_0")
    val weapon0: Weapon? = null,

    @SerialName(value = "weapon_1")
    val weapon1: Weapon? = null,

    @SerialName(value = "weapon_2")
    val weapon2: Weapon? = null,

    @SerialName(value = "weapon_3")
    val weapon3: Weapon? = null,

    @SerialName(value = "weapon_4")
    val weapon4: Weapon? = null,

    @SerialName(value = "weapon_5")
    val weapon5: Weapon? = null,

    @SerialName(value = "weapon_6")
    val weapon6: Weapon? = null,

    @SerialName(value = "weapon_7")
    val weapon7: Weapon? = null,

    @SerialName(value = "weapon_8")
    val weapon8: Weapon? = null,

    @SerialName(value = "weapon_9")
    val weapon9: Weapon? = null
)