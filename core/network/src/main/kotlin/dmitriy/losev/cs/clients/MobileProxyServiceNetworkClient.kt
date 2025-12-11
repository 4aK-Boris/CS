package dmitriy.losev.cs.clients

import dmitriy.losev.cs.Context
import dmitriy.losev.cs.proxy.Service
import io.ktor.client.call.body
import kotlinx.serialization.Serializable
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Singleton

@Singleton
class MobileProxyServiceNetworkClient(
    @Provided context: Context,
    @Provided private val httpClientHandler: HttpClientHandler
) : BaseNetworkClient() {

    override val service = Service.MobileProxy(host = context.mobileProxyConfig.host)

    suspend fun rotate(deviceId: String): RotateResponse {
        return getRequest(
            httpClient = httpClientHandler.httpClient,
            handle = "/rotate",
            params = mapOf("device_id" to deviceId)
        ).body()
    }

    suspend fun health(): HealthResponse {
        return getRequest(
            httpClient = httpClientHandler.httpClient,
            handle = "/health"
        ).body()
    }
}

@Serializable
data class RotateResponse(
    val success: Boolean,
    val error: String? = null
)

@Serializable
data class HealthResponse(
    val status: String
)
