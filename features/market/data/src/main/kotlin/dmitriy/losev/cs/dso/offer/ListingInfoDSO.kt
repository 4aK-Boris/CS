package dmitriy.losev.cs.dso.offer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListingInfoDSO(

    @SerialName(value = "listingid")
    val listingId: String,

    @SerialName(value = "price")
    val price: Int,

    @SerialName(value = "fee")
    val fee: Int,

    @SerialName(value = "publisher_fee_app")
    val publisherFeeApp: Int,

    @SerialName(value = "publisher_fee_percent")
    val publisherFeePercent: String,

    @SerialName(value = "currencyid")
    val currencyId: Int,

    @SerialName(value = "steam_fee")
    val steamFee: Int,

    @SerialName(value = "publisher_fee")
    val publisherFee: Int,

    @SerialName(value = "converted_price")
    val convertedPrice: Int,

    @SerialName(value = "converted_fee")
    val convertedFee: Int,

    @SerialName(value = "converted_currencyid")
    val convertedCurrencyId: Int,

    @SerialName(value = "converted_steam_fee")
    val convertedSteamFee: Int,

    @SerialName(value = "converted_publisher_fee")
    val convertedPublisherFee: Int,

    @SerialName(value = "converted_price_per_unit")
    val convertedPricePerUnit: Int,

    @SerialName(value = "converted_fee_per_unit")
    val convertedFeePerUnit: Int,

    @SerialName(value = "converted_steam_fee_per_unit")
    val convertedSteamFeePerUnit: Int,

    @SerialName(value = "converted_publisher_fee_per_unit")
    val convertedPublisherFeePerUnit: Int,

    @SerialName(value = "asset")
    val asset: ListingAssetDSO
)
