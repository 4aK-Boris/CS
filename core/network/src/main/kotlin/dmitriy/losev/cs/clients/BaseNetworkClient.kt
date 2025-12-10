package dmitriy.losev.cs.clients

import dmitriy.losev.cs.proxy.Service
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import io.ktor.http.URLProtocol
import io.ktor.http.parameters
import io.ktor.http.path
import io.ktor.util.appendAll

abstract class BaseNetworkClient {

    abstract val service: Service

    open val protocol: URLProtocol = URLProtocol.HTTP

    open val defaultHeaders: Map<String, String> = emptyMap()

    suspend inline fun <reified T : Any> postRequest(
        httpClient: HttpClient,
        handle: String,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap(),
        body: T
    ): HttpResponse {
        return httpClient.post {
            setBody(body = body)
            setUrl(handle)
            setParams(params)
            setHeaders(headers)
        }
    }

    suspend inline fun postRequestWithUrlEncodedForm(
        httpClient: HttpClient,
        handle: String,
        formParams: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): HttpResponse {
        return httpClient.submitForm(
            formParameters = getFormParameters(formParams)
        ) {
            setUrl(handle)
            setHeaders(headers)
        }
    }

    suspend inline fun postRequestWithMultiPartForm(
        httpClient: HttpClient,
        handle: String,
        boundary: String,
        formText: ByteArray,
        headers: Map<String, String> = emptyMap()
    ): HttpResponse {
        return httpClient.post {
            setUrl(handle)
            setHeaders(headers)
            setBody(body = formText)
            header(key = HttpHeaders.ContentType, value = "multipart/form-data; boundary=$boundary")
        }
    }

    suspend inline fun getRequest(
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

    fun getFormParameters(formParams: Map<String, String>): Parameters {
        return parameters { appendAll(values = formParams) }
    }

    fun HttpRequestBuilder.setUrl(handle: String) {
        url {
            protocol = this@BaseNetworkClient.protocol
            host = service.host
            path(handle)
        }
    }

    fun HttpRequestBuilder.setParams(params: Map<String, String>) {
        params.forEach { (name, value) ->
            parameter(key = name, value = value)
        }
    }

    fun HttpRequestBuilder.setHeaders(headers: Map<String, String>) {
        defaultHeaders.forEach { (name, value) ->
            header(key = name, value = value)
        }
        headers.forEach { (name, value) ->
            header(key = name, value = value)
        }
    }
}
