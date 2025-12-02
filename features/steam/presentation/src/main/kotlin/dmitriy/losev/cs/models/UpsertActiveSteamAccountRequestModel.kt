package dmitriy.losev.cs.models

import dmitriy.losev.cs.EMPTY_STRING
import kotlinx.serialization.Serializable

@Serializable
data class UpsertActiveSteamAccountRequestModel(
    val steamId: String,
    val marketCSGOApiToken: String
) {
    companion object {

        val example = UpsertActiveSteamAccountRequestModel(steamId = "76561198012345678", marketCSGOApiToken = "JQuZsUnNPc6g4Md2WtSm465C5w32321")

        val emptyExample = UpsertActiveSteamAccountRequestModel(steamId = EMPTY_STRING, marketCSGOApiToken = EMPTY_STRING)
    }
}
