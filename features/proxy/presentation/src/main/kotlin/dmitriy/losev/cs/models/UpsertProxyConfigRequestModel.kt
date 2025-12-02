package dmitriy.losev.cs.models

import kotlinx.serialization.Serializable

@Serializable
data class UpsertProxyConfigRequestModel(
    val host: String,
    val port: Int,
    val login: String,
    val password: String
) {

    companion object {

        val example1 = UpsertProxyConfigRequestModel(host = "192.168.0.1", port = 8080, login = "login", password = "password")

        val example2 = UpsertProxyConfigRequestModel(host = "192.168.0.2", port = 8000, login = "login", password = "password")
    }
}
