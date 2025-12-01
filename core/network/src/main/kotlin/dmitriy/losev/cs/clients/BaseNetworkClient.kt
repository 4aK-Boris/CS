package dmitriy.losev.cs.clients

import dmitriy.losev.cs.proxy.Service
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Parameters
import io.ktor.http.URLProtocol
import io.ktor.http.parameters
import io.ktor.http.path
import io.ktor.util.appendAll
import io.ktor.util.reflect.TypeInfo
import kotlin.reflect.KClass

abstract class BaseNetworkClient {

    abstract val service: Service

    open val protocol: URLProtocol = URLProtocol.HTTP

    open val defaultHeaders: Map<String, String> = emptyMap()

    protected suspend fun <T : Any> postRequest(
        httpClient: HttpClient,
        handle: String,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap(),
        requestClazz: KClass<T>,
        body: T
    ): HttpResponse {
        return httpClient.post(urlString = "${service.host}$handle") {
            setBody(body = body, bodyType = TypeInfo(type = requestClazz))
            setUrl(handle)
            setParams(params)
            setHeaders(headers)
        }
    }

    protected suspend fun postRequestWithUrlEncodedForm(
        httpClient: HttpClient,
        handle: String,
        formParams: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): HttpResponse {
        return httpClient.submitForm(
            url = "${service.host}$handle",
            formParameters = getFormParameters(formParams)
        ) {
            setHeaders(headers)
        }
    }

    protected suspend fun getRequest(
        httpClient: HttpClient,
        handle: String,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): HttpResponse {
         return httpClient.get {
            setUrl(handle)
            setParams(params)
            setHeaders(headers)
        }
    }

    protected suspend fun <T: Any> HttpResponse.getResponseBody(responseClazz: KClass<T>): T {
        return body(typeInfo = TypeInfo(type = responseClazz))
    }

    protected suspend fun <T: Any> HttpResponse.getResponseBody(typeInfo: TypeInfo): T {
        return body(typeInfo = typeInfo)
    }

    private fun getFormParameters(formParams: Map<String, String>): Parameters {
        return parameters { appendAll(values = formParams) }
    }

    private fun HttpRequestBuilder.setUrl(handle: String) {
        url {
            protocol = this@BaseNetworkClient.protocol
            host = service.host
            path(handle)
        }
    }

    private fun HttpRequestBuilder.setParams(params: Map<String, String>) {
        params.forEach { (name, value) ->
            parameter(key = name, value = value)
        }
    }

    private fun HttpRequestBuilder.setHeaders(headers: Map<String, String>) {
        defaultHeaders.forEach { (name, value) ->
            header(key = name, value = value)
        }
        headers.forEach { (name, value) ->
            header(key = name, value = value)
        }
    }
}
