package dmitriy.losev.cs.dso.offer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppDataDSO(

    @SerialName(value = "appid")
    val appId: Int,

    @SerialName(value = "name")
    val name: String,

    @SerialName(value = "icon")
    val icon: String,

    @SerialName(value = "link")
    val link: String
)