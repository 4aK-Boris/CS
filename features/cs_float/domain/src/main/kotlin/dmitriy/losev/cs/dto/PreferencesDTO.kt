package dmitriy.losev.cs.dto

data class PreferencesDTO(
    val localizeItemNames: Boolean,
    val maxOfferDiscount: Int,
    val offersEnabled: Boolean?
)
