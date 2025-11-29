package dmitriy.losev.cs.dso

import kotlinx.serialization.Serializable

@Serializable
data class ItemsFloatDSO(
    val items: Map<String, ItemFloatDSO>
)