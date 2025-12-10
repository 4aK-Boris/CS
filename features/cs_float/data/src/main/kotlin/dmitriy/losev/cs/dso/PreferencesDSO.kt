package dmitriy.losev.cs.dso


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PreferencesDSO(

    @SerialName(value = "localize_item_names")
    val localizeItemNames: Boolean,

    @SerialName(value = "max_offer_discount")
    val maxOfferDiscount: Int,

    @SerialName(value = "offers_enabled")
    val offersEnabled: Boolean?
)
