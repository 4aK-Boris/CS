package dmitriy.losev.cs.models

import dmitriy.losev.cs.EMPTY_STRING
import kotlinx.serialization.Serializable

@Serializable
data class DeleteProxyConfigRequestModel(val host: String, val port: Int) {

    companion object {

        val example = DeleteProxyConfigRequestModel(host = "192.168.0.1", port = 8080)

        val emptyExample = DeleteProxyConfigRequestModel(host = EMPTY_STRING, port = 0)

    }
}
