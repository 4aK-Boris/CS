package dmitriy.losev.cs.dto.skins

data class SkinsAvailabilityResponseDTO(
    val availableSkins: Map<Int, Double>,
    val unavailableSkinIds: List<Int>
)