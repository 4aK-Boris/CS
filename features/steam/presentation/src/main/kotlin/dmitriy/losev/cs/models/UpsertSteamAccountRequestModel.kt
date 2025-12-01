package dmitriy.losev.cs.models

import dmitriy.losev.cs.EMPTY_STRING
import kotlinx.serialization.Serializable

@Serializable
data class UpsertSteamAccountRequestModel(
    val steamId: String,
    val login: String,
    val password: String,
    val sharedSecret: String,
    val identitySecret: String,
    val revocationCode: String,
    val deviceId: String
) {
    companion object {

        val example = UpsertSteamAccountRequestModel(
            steamId = "76561198012345678",
            login = "my_steam_login",
            password = "secure_password",
            sharedSecret = "ABCDEF123456==",
            identitySecret = "GHIJKL789012==",
            revocationCode = "R12345",
            deviceId = "android:12345678-1234-1234-1234-123456789012"
        )

        val emptyExample = UpsertSteamAccountRequestModel(
            steamId = EMPTY_STRING,
            login = EMPTY_STRING,
            password = EMPTY_STRING,
            sharedSecret = EMPTY_STRING,
            identitySecret = EMPTY_STRING,
            revocationCode = EMPTY_STRING,
            deviceId = EMPTY_STRING
        )
    }
}
