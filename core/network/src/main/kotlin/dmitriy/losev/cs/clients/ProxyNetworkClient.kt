package dmitriy.losev.cs.clients

import dmitriy.losev.cs.proxy.ProxyClient
import dmitriy.losev.cs.proxy.ProxyClients
import dmitriy.losev.cs.proxy.ProxyStats
import dmitriy.losev.cs.proxy.Service
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.cookie
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import io.ktor.http.headers
import io.ktor.http.parameters
import io.ktor.http.path
import io.ktor.util.reflect.TypeInfo
import java.net.SocketException
import java.net.UnknownHostException
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.reflect.KClass

abstract class ProxyNetworkClient(private val proxyClients: ProxyClients) {

    private val mutex = Mutex()

    abstract val service: Service

    open val protocol = URLProtocol.HTTPS

    open val headers = mapOf<String, String>()

    companion object {
        private const val RATE_LIMIT_MS = 3000L
        private const val FREEZE_DURATION_MS = 60000L

        private fun formatException(e: Exception): String {
            return when (e) {
                is ClientRequestException -> "HTTP ${e.response.status.value} ${e.response.status.description}"
                is HttpRequestTimeoutException -> "Request timeout (${e.message})"
                is ConnectTimeoutException -> "Connection timeout - proxy not responding"
                is SocketTimeoutException -> "Socket timeout - slow response"
                is SocketException -> "Socket error: ${e.message}"
                is UnknownHostException -> "Unknown host: ${e.message}"
                else -> {
                    val cause = e.cause
                    val causeInfo = if (cause != null && cause != e) {
                        " | Cause: ${cause::class.simpleName}: ${cause.message}"
                    } else ""
                    "${e::class.simpleName}: ${e.message ?: "Unknown error"}$causeInfo"
                }
            }
        }
    }

    private suspend fun selectBestProxy(): ProxyClient {

        return mutex.withLock {

            // Фильтруем незамороженные и здоровые прокси
            val availableProxies = proxyClients.clients.filter { proxyClient ->
                val state = proxyClient.getState(service)
                state.isHealthy && !state.isFrozen()
            }

            if (availableProxies.isEmpty()) {
                // Если все прокси недоступны, сбрасываем флаги здоровья и ищем незамороженные
                proxyClients.clients.forEach { proxyClient -> proxyClient.getState(service).isHealthy = true }
                val unfrozenProxies = proxyClients.clients.filter { !it.getState(service).isFrozen() }

                if (unfrozenProxies.isEmpty()) {
                    // Если все прокси заморожены, возвращаем первый (придется подождать)
                    proxyClients.clients.first()
                } else {
                    unfrozenProxies.first()
                }
            } else {
                availableProxies.maxByOrNull { proxyClient ->

                    val successRate = if (proxyClient.getState(service).requestCount > 0) {
                        proxyClient.getState(service).successCount.toFloat() / proxyClient.getState(service).requestCount
                    } else {
                        1f
                    }

                    val timeSinceLastUse = System.currentTimeMillis() - proxyClient.getState(service).lastUsed
                    val responseTimeFactor = 1000f / (proxyClient.getState(service).avgResponseTime + 1)

                    successRate * timeSinceLastUse * responseTimeFactor

                } ?: availableProxies.first()
            }
        }
    }

    private suspend fun waitForRateLimit(proxyClient: ProxyClient) {
        val state = proxyClient.getState(service)
        val timeSinceLastUse = System.currentTimeMillis() - state.lastUsed

        if (timeSinceLastUse < RATE_LIMIT_MS) {
            val delayTime = RATE_LIMIT_MS - timeSinceLastUse
            println("Rate limit: waiting ${delayTime}ms for proxy ${proxyClient.config.name}")
            delay(delayTime)
        }
    }

    suspend fun <T : Any> get(
        handle: String,
        maxRetries: Int = 20,
        responseClazz: KClass<T>,
        params: Map<String, String> = emptyMap()
    ): T {

        var lastException: Exception? = null

        repeat(maxRetries) { attempt ->

            val proxyClient = selectBestProxy()

            waitForRateLimit(proxyClient)

            val startTime = System.currentTimeMillis()

            try {
                println("[${service.name}] GET request to $handle via proxy ${proxyClient.config.name} (${proxyClient.config.host}:${proxyClient.config.port})")

                val responseBody = proxyClient.client.get {
                    setUrl(handle)
                    setParams(params)
                    setHeaders(this@ProxyNetworkClient.headers)
                    setCookie()
                }.body<T>(typeInfo = TypeInfo(type = responseClazz))

                val responseTime = System.currentTimeMillis() - startTime

                println("[${service.name}] GET request SUCCESS via proxy ${proxyClient.config.name}, response time: ${responseTime}ms")

                updateStats(proxyClient, success = true, responseTime)

                return responseBody
            } catch (e: Exception) {

                lastException = e

                val responseTime = System.currentTimeMillis() - startTime

                // Детальное логирование ошибки
                val errorDetails = formatException(e)
                println("[${service.name}] GET request FAILED via proxy ${proxyClient.config.name}: $errorDetails")

                // Проверяем на код 429 (Too Many Requests)
                if (e is ClientRequestException && e.response.status == HttpStatusCode.TooManyRequests) {
                    println("[${service.name}] 429 Too Many Requests for proxy ${proxyClient.config.name}. Freezing for ${FREEZE_DURATION_MS}ms")
                    mutex.withLock {
                        proxyClient.getState(service).freeze(FREEZE_DURATION_MS)
                    }
                }

                updateStats(proxyClient, success = false, responseTime)

                if (attempt < maxRetries - 1) {
                    println("[${service.name}] Retrying in ${1000 * (attempt + 1)}ms... (attempt ${attempt + 2}/$maxRetries)")
                    delay(1000)
                }
            }
        }

        throw lastException ?: Exception("Все попытки исчерпаны")
    }

    suspend fun <T : Any, R : Any> post(
        handle: String,
        maxRetries: Int = 20,
        requestClazz: KClass<T>,
        responseClazz: KClass<R>,
        body: T,
        params: Map<String, String> = emptyMap()
    ): R {

        var lastException: Exception? = null

        repeat(maxRetries) { attempt ->

            val proxyClient = selectBestProxy()

            // Ожидание rate limit перед запросом
            waitForRateLimit(proxyClient)

            val startTime = System.currentTimeMillis()

            try {
                println("[${service.name}] POST request to $handle via proxy ${proxyClient.config.name} (${proxyClient.config.host}:${proxyClient.config.port})")

                val responseBody = proxyClient.client.post {
                    setBody(body = body, bodyType = TypeInfo(type = requestClazz))
                    setUrl(handle)
                    setParams(params)
                    setHeaders(this@ProxyNetworkClient.headers)
                }.body<R>(typeInfo = TypeInfo(type = responseClazz))

                val responseTime = System.currentTimeMillis() - startTime

                println("[${service.name}] POST request SUCCESS via proxy ${proxyClient.config.name}, response time: ${responseTime}ms")

                updateStats(proxyClient, success = true, responseTime)

                return responseBody
            } catch (e: Exception) {

                lastException = e

                val responseTime = System.currentTimeMillis() - startTime

                // Детальное логирование ошибки
                val errorDetails = formatException(e)
                println("[${service.name}] POST request FAILED via proxy ${proxyClient.config.name}: $errorDetails")

                // Проверяем на код 429 (Too Many Requests)
                if (e is ClientRequestException && e.response.status == HttpStatusCode.TooManyRequests) {
                    println("[${service.name}] 429 Too Many Requests for proxy ${proxyClient.config.name}. Freezing for ${FREEZE_DURATION_MS}ms")
                    mutex.withLock {
                        proxyClient.getState(service).freeze(FREEZE_DURATION_MS)
                    }
                }

                updateStats(proxyClient, success = false, responseTime)

                if (attempt < maxRetries - 1) {
                    println("[${service.name}] Retrying in ${1000 * (attempt + 1)}ms... (attempt ${attempt + 2}/$maxRetries)")
                    delay(1000)
                }
            }
        }

        throw lastException ?: Exception("Все попытки исчерпаны")
    }

    private fun HttpRequestBuilder.setUrl(handle: String) {
        url {
            protocol = this@ProxyNetworkClient.protocol
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
        headers.forEach { (name, value) ->
            header(key = name, value = value)
        }
    }

    private fun HttpRequestBuilder.setCookie() {
        apply {
            cookie(
                name = "ak_bmsc",
                value = "4A6BC3A3A6FE5BB12EC9202C8890E97A~000000000000000000000000000000~YAAQpHp7XBoGRzKaAQAAZ6P+ZB1fp5IU3+hLEzNipu394fICnNp3aE6B+v72vQ5wo+w7vYNeq6PXQL6FLeBivBDOlo4Ze9xqk5dXlXw7o4hM3lbCB2k7Qwc5cgEj631VtWMH6Qol0jx7ZkkoPP5HwQBhIk20j5Ar5bYoNQ3iVLLiI8UzZgo6Bzum5KtBUeiNcm70P65po6Ygy5JpaGEe+BhDbjl/oISdgUwr3r6WAXr0xBjCNS7qBFgqME25GFZWUmBi3SL8Gupkzgx80m+B5AH9jp+WrF0AQZ6QbNVBhOus51HEEQz6+86ZujDakmdCkjAHJMSHVToQUnlQLy/q5NMajNNnLwR+b2QsyBa1dtuQlYIisETw4kOlPXvTjm0r4OH5m49lDA4="
            )
            cookie(
                name = "bm_sv",
                value = "C9F6BD0F9B5587269F66FFFCD3D07F34~YAAQpHp7XDcIRzKaAQAAv+7+ZB0DuTBxPVLtWbEJKkscT8kGMmpnYajQgwWB9mwtmajDFOrkhs6t5m9vdkul8hpHo3Z6Fr2eK6GeQo6u7+sbgdmJWcRzWZM0sihAz73t8CgD3Hi5oqv75eze12VuZAbfRhgdH2nkWiWYzt4iU3IRskujUGQlxugojw2lfBxwaNlHAreJ9HavSHllh6TwpUUr5B2mF2zeA0LzPr7y0Whs8hrw8rG3N6XIz84ftJgaLTmQnpLI~1"
            )
            cookie(name = "browserid", value = "205599655912000139")
            cookie(
                name = "cookieSettings",
                value = "%7B%22version%22%3A1%2C%22preference_state%22%3A1%2C%22content_customization%22%3Anull%2C%22valve_analytics%22%3Anull%2C%22third_party_analytics%22%3Anull%2C%22third_party_content%22%3Anull%2C%22utm_enabled%22%3Atrue%7D"
            )
            cookie(name = "sessionid", value = "8dea807530656599e96357d0")
            cookie(name = "steamCountry", value = "FI%7C817dd35f541c03cabd8c6e91f50913c7")
            cookie(
                name = "steamLoginSecure",
                value = "76561198780877815%7C%7CeyAidHlwIjogIkpXVCIsICJhbGciOiAiRWREU0EiIH0.eyAiaXNzIjogInI6MDAxN18yNzM0MkRCMl9GNzQyQiIsICJzdWIiOiAiNzY1NjExOTg3ODA4Nzc4MTUiLCAiYXVkIjogWyAid2ViOmNvbW11bml0eSIgXSwgImV4cCI6IDE3NjI3MTc2ODIsICJuYmYiOiAxNzUzOTkxMDIwLCAiaWF0IjogMTc2MjYzMTAyMCwgImp0aSI6ICIwMDBEXzI3MzQyREIzXzFFN0U0IiwgIm9hdCI6IDE3NjI2MzEwMTksICJydF9leHAiOiAxNzgwOTM4MDMzLCAicGVyIjogMCwgImlwX3N1YmplY3QiOiAiODUuOTAuMjA4LjExOCIsICJpcF9jb25maXJtZXIiOiAiODUuOTAuMjA4LjExOCIgfQ.QN12anrxJ6mfWrn2x4B4vLFpz3O4Iej2YriEScGyJChwSlT4Zp-haTdVLN0bJECe6yzPnJYu9g6EqZk4dzArBw"
            )
            cookie(name = "timezoneName", value = "America%2FLos_Angeles")
            cookie(name = "timezoneOffset", value = "10800,0")
            cookie(
                name = "webTradeEligibility",
                value = "%7B%22allowed%22%3A0%2C%22reason%22%3A16424%2C%22allowed_at_time%22%3A1763235839%2C%22steamguard_required_days%22%3A15%2C%22new_device_cooldown_days%22%3A0%2C%22expiration%22%3A1762631339%2C%22time_checked%22%3A1762631039%7D"
            )
        }
    }

    private suspend fun updateStats(
        proxyClient: ProxyClient,
        success: Boolean,
        responseTime: Long
    ) {
        mutex.withLock {

            proxyClient.getState(service).requestCount++
            proxyClient.getState(service).lastUsed = System.currentTimeMillis()

            if (success) {
                proxyClient.getState(service).successCount++

                val count = proxyClient.getState(service).successCount

                proxyClient.getState(service).avgResponseTime = (proxyClient.getState(service).avgResponseTime * (count - 1) + responseTime) / count

            } else {

                proxyClient.getState(service).failureCount++

                val failureRate = proxyClient.getState(service).failureCount.toFloat() / proxyClient.getState(service).requestCount

                if (failureRate > 0.5 && proxyClient.getState(service).requestCount > 5) {
                    proxyClient.getState(service).isHealthy = false
                }
            }
        }
    }

    suspend fun getStatsForService(): List<ProxyStats> {

        return mutex.withLock {

            proxyClients.clients.map { proxyClient ->

                val successRate = if (proxyClient.getState(service).requestCount > 0) {
                    (proxyClient.getState(service).successCount.toFloat() / proxyClient.getState(service).requestCount * 100).toInt()
                } else {
                    0
                }

                ProxyStats(
                    name = proxyClient.config.name,
                    requestCount = proxyClient.getState(service).requestCount,
                    successCount = proxyClient.getState(service).successCount,
                    failureCount = proxyClient.getState(service).failureCount,
                    avgResponseTime = proxyClient.getState(service).avgResponseTime,
                    isHealthy = proxyClient.getState(service).isHealthy,
                    successRate = successRate
                )

            }
        }
    }

    fun closeAllForService() {
        proxyClients.clients.forEach { proxyClient -> proxyClient.client.close() }
    }
}