package dmitriy.losev.cs.clients

import io.ktor.util.reflect.typeInfo
import kotlin.reflect.KClass

abstract class NetworkClient(private val httpClientHandler: HttpClientHandler): BaseNetworkClient() {

    suspend fun <T : Any, R : Any> post(
        handle: String,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap(),
        requestClazz: KClass<T>,
        responseClazz: KClass<R>,
        body: T
    ): R {
        return postRequest(
            httpClient = httpClientHandler.httpClient,
            handle = handle,
            requestClazz = requestClazz,
            params = params,
            headers = headers,
            body = body
        ).getResponseBody(responseClazz)
    }

    suspend fun <R : Any> get(
        handle: String,
        responseClazz: KClass<R>,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): R {
        return getRequest(
            httpClient = httpClientHandler.httpClient,
            handle = handle,
            params = params,
            headers = headers
        ).getResponseBody(responseClazz)
    }

    suspend fun <T : Any, R : Any> postList(
        handle: String,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap(),
        requestClazz: KClass<T>,
        body: T
    ): List<R> {
        return postRequest(
            httpClient = httpClientHandler.httpClient,
            handle = handle,
            requestClazz = requestClazz,
            params = params,
            headers = headers,
            body = body
        ).getResponseBody(typeInfo = typeInfo<List<KClass<R>>>())
    }
}
