package dmitriy.losev.cs.dso

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemFloatDSO(

    @SerialName(value = "itemid")
    val itemId: ULong,

    @SerialName(value = "defindex")
    val defIndex: Int,

    @SerialName(value = "paintindex")
    val paintIndex: Int,

    @SerialName(value = "rarity")
    val rarity: Int,

    @SerialName(value = "quality")
    val quality: Int,

    @SerialName(value = "paintseed")
    val paintSeed: Int,

    @SerialName(value = "inventory")
    val inventory: ULong,

    @SerialName(value = "origin")
    val origin: ULong,

    @SerialName(value = "floatvalue")
    val float: Double,

    @SerialName(value = "s")
    val s: ULong,

    @SerialName(value = "a")
    val a: ULong,

    @SerialName(value = "d")
    val d: ULong,

    @SerialName(value = "m")
    val m: ULong,

    @SerialName(value = "keychains")
    val keyChains: List<KeyChainDSO>,

    @SerialName(value = "stickers")
    val stickers: List<String>
)