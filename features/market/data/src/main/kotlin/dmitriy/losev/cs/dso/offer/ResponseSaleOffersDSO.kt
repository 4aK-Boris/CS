package dmitriy.losev.cs.dso.offer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSaleOffersDSO(

    @SerialName("success")
    val success: Boolean,

    @SerialName("start")
    val start: Int,

    @SerialName("pagesize")
    val pageSize: Int,

    @SerialName("total_count")
    val totalCount: Int,

    @SerialName("listinginfo")
    val listingInfo: Map<String, ListingInfoDSO>,

    @SerialName("assets")
    val assets: Map<String, Map<String, Map<String, AssetDSO>>>,

    @SerialName("currency")
    val currency: List<String>,

    @SerialName("hovers")
    val hovers: String,

    @SerialName("app_data")
    val appData: Map<String, AppDataDSO>
)