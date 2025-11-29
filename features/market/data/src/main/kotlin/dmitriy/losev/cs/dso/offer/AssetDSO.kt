package dmitriy.losev.cs.dso.offer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssetDSO(

    @SerialName(value = "currency")
    val currency: Int,

    @SerialName(value = "appid")
    val appId: Int,

    @SerialName(value = "contextid")
    val contextId: String,

    @SerialName(value = "id")
    val id: String,

    @SerialName(value = "classid")
    val classId: String,

    @SerialName(value = "instanceid")
    val instanceId: String,

    @SerialName(value = "amount")
    val amount: String,

    @SerialName(value = "status")
    val status: Int,

    @SerialName(value = "original_amount")
    val originalAmount: String,

    @SerialName(value = "unowned_id")
    val unownedId: String,

    @SerialName(value = "unowned_contextid")
    val unownedContextId: String,

    @SerialName(value = "background_color")
    val backgroundColor: String,

    @SerialName(value = "icon_url")
    val iconUrl: String,

    @SerialName(value = "descriptions")
    val descriptions: List<DescriptionDSO>,

    @SerialName(value = "tradable")
    val tradable: Int,

    @SerialName(value = "actions")
    val actions: List<ActionDSO>,

    @SerialName(value = "name")
    val name: String,

    @SerialName(value = "name_color")
    val nameColor: String,

    @SerialName(value = "type")
    val type: String,

    @SerialName(value = "market_name")
    val marketName: String,

    @SerialName(value = "market_hash_name")
    val marketHashName: String,

    @SerialName(value = "market_actions")
    val marketActions: List<MarketActionDSO>,

    @SerialName(value = "commodity")
    val commodity: Int,

    @SerialName(value = "market_tradable_restriction")
    val marketTradableRestriction: Int,

    @SerialName(value = "market_marketable_restriction")
    val marketMarketableRestriction: Int,

    @SerialName(value = "marketable")
    val marketable: Int,

    @SerialName(value = "sealed")
    val sealed: Int,

    @SerialName(value = "app_icon")
    val appIcon: String,

    @SerialName(value = "owner")
    val owner: Int
)