package dmitriy.losev.cs.dso.skins


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SkinsAvailabilityDataDSO(

    @SerialName(value = "available_skins")
    val availableSkins: Map<Int, Double>,

    @SerialName(value = "unavailable_skin_ids")
    val unavailableSkinIds: List<Int>
)