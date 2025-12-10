package dmitriy.losev.cs.clients

import io.ktor.client.call.body

abstract class NetworkClient(val httpClientHandler: HttpClientHandler): BaseNetworkClient() {

    suspend inline fun <reified T : Any, reified R : Any> post(
        handle: String,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap(),
        body: T
    ): R {
        return postRequest(
            httpClient = httpClientHandler.httpClient,
            handle = handle,
            params = params,
            headers = headers,
            body = body
        ).body()
    }

    suspend inline fun <reified R : Any> get(
        handle: String,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): R {
        return getRequest(
            httpClient = httpClientHandler.httpClient,
            handle = handle,
            params = params,
            headers = headers
        ).body()
    }

    suspend inline fun <reified T : Any, reified R : Any> postList(
        handle: String,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap(),
        body: T
    ): List<R> {
        return postRequest(
            httpClient = httpClientHandler.httpClient,
            handle = handle,
            params = params,
            headers = headers,
            body = body
        ).body()
    }
}
