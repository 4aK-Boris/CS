package dmitriy.losev.cs.dso.item

import dmitriy.losev.cs.EMPTY_STRING
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultDSO(

    @SerialName(value = "name")
    val name: String = EMPTY_STRING,

    @SerialName(value = "hash_name")
    val hashName: String = EMPTY_STRING,

    @SerialName(value = "sell_listings")
    val sellListings: Int = 0,

    @SerialName(value = "sell_price")
    val sellPrice: Int = 0,

    @SerialName(value = "sell_price_text")
    val sellPriceText: String = EMPTY_STRING,

    @SerialName(value = "app_icon")
    val appIcon: String = EMPTY_STRING,

    @SerialName(value = "app_name")
    val appName: String = EMPTY_STRING,

    @SerialName(value = "asset_description")
    val assetDescription: AssetDescriptionDSO = AssetDescriptionDSO(),

    @SerialName(value = "sale_price_text")
    val salePriceText: String = EMPTY_STRING,
)