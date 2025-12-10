package dmitriy.losev.cs.clients

import dmitriy.losev.cs.proxy.Service
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.statement.bodyAsText
import io.ktor.http.URLProtocol
import io.ktor.http.isSuccess
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.koin.core.annotation.Singleton

@Singleton
class MarketCSGOProxyClient(private val httpClientHandler: HttpClientHandler) : BaseNetworkClient() {

    override val service = Service.MARKET_CSGO

    override val protocol = URLProtocol.HTTPS
    private val clientCache = mutableMapOf<Long, HttpClient>()
    private val mutex = Mutex()

    suspend fun getClientForSteamId(steamId: Long): HttpClient {
        return mutex.withLock {
            clientCache.getOrPut(key = steamId) {
                httpClientHandler.getProxyHttpClient(steamId)
            }
        }
    }

    suspend inline fun <reified R : Any> get(
        steamId: Long,
        handle: String,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): R {
        return getRequest(
            httpClient = getClientForSteamId(steamId),
            handle = handle,
            params = params,
            headers = headers
        ).body()
    }

    suspend inline fun getWithTextBody(
        steamId: Long,
        handle: String,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): String {
        return getRequest(
            httpClient = getClientForSteamId(steamId),
            handle = handle,
            params = params,
            headers = headers
        ).bodyAsText()
    }

    suspend inline fun getWithoutResponse(
        steamId: Long,
        handle: String,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): Boolean {
        val response = getRequest(
            httpClient = getClientForSteamId(steamId),
            handle = handle,
            params = params,
            headers = headers
        )
        return response.status.isSuccess()
    }

    suspend inline fun <reified T : Any, reified R : Any> post(
        steamId: Long,
        handle: String,
        body: T,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): R {
        return postRequest(
            httpClient = getClientForSteamId(steamId),
            handle = handle,
            params = params,
            headers = headers,
            body = body
        ).body()
    }

    suspend inline fun <reified R : Any> postWithUrlEncodedForm(
        steamId: Long,
        handle: String,
        formParams: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): R {
        return postRequestWithUrlEncodedForm(
            httpClient = getClientForSteamId(steamId),
            handle = handle,
            formParams = formParams,
            headers = headers
        ).body()
    }

    suspend fun closeAll() {
        mutex.withLock {
            clientCache.values.forEach(action = HttpClient::close)
            clientCache.clear()
        }
    }

    suspend fun closeSteamIdClient(steamId: Long) {
        mutex.withLock {
            clientCache.remove(steamId)?.close()
        }
    }
}
