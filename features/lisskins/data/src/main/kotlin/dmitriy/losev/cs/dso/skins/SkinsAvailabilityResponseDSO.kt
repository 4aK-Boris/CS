package dmitriy.losev.cs.dso.skins


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SkinsAvailabilityResponseDSO(

    @SerialName(value = "data")
    val skinsAvailabilityData: SkinsAvailabilityDataDSO
)