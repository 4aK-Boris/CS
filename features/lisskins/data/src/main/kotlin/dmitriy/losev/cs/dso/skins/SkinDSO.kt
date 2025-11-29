package dmitriy.losev.cs.dso.skins


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Serializable
data class SkinDSO(

    @SerialName(value = "id")
    val id: Int,

    @SerialName(value = "name")
    val name: String,

    @SerialName(value = "price")
    val price: Double,

    @SerialName(value = "unlock_at")
    val unlockAt: Instant? = null,

    @SerialName(value = "item_class_id")
    val itemClassId: String,

    @SerialName(value = "created_at")
    val createdAt: Instant,

    @SerialName(value = "item_float")
    val itemFloat: String,

    @SerialName(value = "name_tag")
    val nameTag: String? = null,

    @SerialName(value = "item_paint_index")
    val itemPaintIndex: Int? = null,

    @SerialName(value = "item_paint_seed")
    val itemPaintSeed: Int? = null,

    @SerialName(value = "stickers")
    val stickers: List<StickerDSO>
)