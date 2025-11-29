package dmitriy.losev.cs.clients

import dmitriy.losev.cs.proxy.Service
import io.ktor.client.HttpClient
import io.ktor.client.statement.bodyAsText
import io.ktor.http.URLProtocol
import io.ktor.http.isSuccess
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.koin.core.annotation.Singleton
import kotlin.reflect.KClass

@Singleton
class MarketCSGOProxyClient(private val httpClientHandler: HttpClientHandler) : BaseNetworkClient() {

    override val service = Service.MARKET_CSGO

    override val protocol = URLProtocol.HTTPS
    private val clientCache = mutableMapOf<ULong, HttpClient>()
    private val mutex = Mutex()

    private suspend fun getClientForSteamId(steamId: ULong): HttpClient {
        return mutex.withLock {
            clientCache.getOrPut(key = steamId) {
                httpClientHandler.getProxyHttpClient(steamId)
            }
        }
    }

    suspend fun <R : Any> get(
        steamId: ULong,
        handle: String,
        responseClazz: KClass<R>,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): R {
        return getRequest(
            httpClient = getClientForSteamId(steamId),
            handle = handle,
            params = params,
            headers = headers
        ).getResponseBody(responseClazz)
    }

    suspend fun  getWithTextBody(
        steamId: ULong,
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

    suspend fun getWithoutResponse(
        steamId: ULong,
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

    suspend fun <T : Any, R : Any> post(
        steamId: ULong,
        handle: String,
        requestClazz: KClass<T>,
        responseClazz: KClass<R>,
        body: T,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): R {
        return postRequest(
            httpClient = getClientForSteamId(steamId),
            handle = handle,
            requestClazz = requestClazz,
            params = params,
            headers = headers,
            body = body
        ).getResponseBody(responseClazz)
    }

    suspend fun <R : Any> postWithUrlEncodedForm(
        steamId: ULong,
        handle: String,
        responseClazz: KClass<R>,
        formParams: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): R {
        return postRequestWithUrlEncodedForm(
            httpClient = getClientForSteamId(steamId),
            handle = handle,
            formParams = formParams,
            headers = headers
        ).getResponseBody(responseClazz)
    }

    suspend fun closeAll() {
        mutex.withLock {
            clientCache.values.forEach(action = HttpClient::close)
            clientCache.clear()
        }
    }

    suspend fun closeSteamIdClient(steamId: ULong) {
        mutex.withLock {
            clientCache.remove(steamId)?.close()
        }
    }
}