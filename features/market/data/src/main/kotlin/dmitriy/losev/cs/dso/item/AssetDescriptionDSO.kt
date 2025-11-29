package dmitriy.losev.cs.dso.item

import dmitriy.losev.cs.EMPTY_STRING
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssetDescriptionDSO(

    @SerialName(value = "appid")
    val appId: Int = 0,

    @SerialName(value = "classid")
    val classId: String = EMPTY_STRING,

    @SerialName(value = "instanceid")
    val instanceId: String = EMPTY_STRING,

    @SerialName(value = "background_color")
    val backgroundColor: String = EMPTY_STRING,

    @SerialName(value = "icon_url")
    val iconUrl: String = EMPTY_STRING,

    @SerialName(value = "tradable")
    val tradable: Int = 0,

    @SerialName(value = "name")
    val name: String = EMPTY_STRING,

    @SerialName(value = "name_color")
    val nameColor: String = EMPTY_STRING,

    @SerialName(value = "type")
    val type: String = EMPTY_STRING,

    @SerialName(value = "market_name")
    val marketName: String = EMPTY_STRING,

    @SerialName(value = "market_hash_name")
    val marketHashName: String = EMPTY_STRING,

    @SerialName(value = "commodity")
    val commodity: Int = 0
)