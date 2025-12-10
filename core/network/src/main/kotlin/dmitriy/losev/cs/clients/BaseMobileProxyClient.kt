package dmitriy.losev.cs.clients

import dmitriy.losev.cs.proxy.MobileProxyManager
import dmitriy.losev.cs.proxy.MobileProxyStats
import dmitriy.losev.cs.proxy.ProxyRateLimiter
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class BaseMobileProxyClient(
    val httpClientHandler: HttpClientHandler,
    val mobileProxyManager: MobileProxyManager,
    val proxyRateLimiter: ProxyRateLimiter
) : BaseNetworkClient() {

    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    val clientCache = mutableMapOf<Long, HttpClient>()
    val mutex = Mutex()

    open val maxRetries: Int = DEFAULT_MAX_RETRIES
    open val rateLimitMs: Long = ProxyRateLimiter.DEFAULT_MIN_INTERVAL_MS

    suspend fun getClientForSteamId(steamId: Long): HttpClient {
        return mutex.withLock {
            clientCache.getOrPut(key = steamId) {
                val proxyConfig = mobileProxyManager.getProxyConfigForSteamId(steamId)
                httpClientHandler.getMobileProxyHttpClient(steamId, proxyConfig)
            }
        }
    }

    suspend fun swapProxyForSteamId(steamId: Long) {
        mutex.withLock {
            clientCache.remove(steamId)?.close()
            val newProxyConfig = mobileProxyManager.swapProxy(steamId)
            clientCache[steamId] = httpClientHandler.getMobileProxyHttpClient(steamId, newProxyConfig)
        }
        logger.info("Swapped proxy for steamId=$steamId to ${mobileProxyManager.getCurrentDeviceName(steamId)}")
    }

    suspend fun swapProxyIfUnhealthy(steamId: Long): Boolean {
        if (!mobileProxyManager.isProxyHealthy(steamId)) {
            swapProxyForSteamId(steamId)
            return true
        }
        return false
    }

    fun recordSuccess(steamId: Long) {
        mobileProxyManager.recordSuccess(steamId)
    }

    fun recordFailure(steamId: Long, error: String? = null) {
        mobileProxyManager.recordFailure(steamId, error)
    }

    fun getProxyStats(): List<MobileProxyStats> {
        return mobileProxyManager.getStats()
    }

    fun getProxyStatsForSteamId(steamId: Long): MobileProxyStats? {
        return mobileProxyManager.getStatsForSteamId(steamId)
    }

    fun getCurrentProxyName(steamId: Long): String? {
        return mobileProxyManager.getCurrentDeviceName(steamId)
    }

    fun isProxyHealthy(steamId: Long): Boolean {
        return mobileProxyManager.isProxyHealthy(steamId)
    }

    suspend fun healProxy(deviceId: String) {
        mobileProxyManager.healProxy(deviceId)
    }

    suspend fun healProxy(deviceId: String, healAction: suspend () -> Unit) {
        mobileProxyManager.healProxy(deviceId, healAction)
    }

    suspend fun healAllUnhealthyProxies() {
        mobileProxyManager.healAllUnhealthyProxies()
    }

    suspend fun healAllUnhealthyProxies(healAction: suspend (deviceId: String) -> Unit) {
        mobileProxyManager.healAllUnhealthyProxies(healAction)
    }

    fun blockProxy(deviceId: String) {
        mobileProxyManager.blockProxy(deviceId)
    }

    fun unblockProxy(deviceId: String) {
        mobileProxyManager.unblockProxy(deviceId)
    }

    fun isProxyBlocked(deviceId: String): Boolean {
        return mobileProxyManager.isProxyBlocked(deviceId)
    }

    fun getBlockedProxies(): List<MobileProxyStats> {
        return mobileProxyManager.getBlockedProxies()
    }

    suspend fun awaitRateLimit(steamId: Long) {
        val deviceId = mobileProxyManager.getCurrentDeviceId(steamId) ?: return
        proxyRateLimiter.acquirePermit(deviceId, service.host, rateLimitMs)
    }

    suspend inline fun <reified R : Any> get(
        steamId: Long,
        handle: String,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): R {
        return executeWithAutoSwap(steamId) {
            awaitRateLimit(steamId)
            getRequest(
                httpClient = getClientForSteamId(steamId),
                handle = handle,
                params = params,
                headers = headers
            ).body()
        }
    }

    suspend inline fun getWithTextBody(
        steamId: Long,
        handle: String,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): String {
        return executeWithAutoSwap(steamId) {
            awaitRateLimit(steamId)
            getRequest(
                httpClient = getClientForSteamId(steamId),
                handle = handle,
                params = params,
                headers = headers
            ).bodyAsText()
        }
    }

    suspend inline fun getWithoutResponse(
        steamId: Long,
        handle: String,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): Boolean {
        return executeWithAutoSwap(steamId) {
            awaitRateLimit(steamId)
            val response = getRequest(
                httpClient = getClientForSteamId(steamId),
                handle = handle,
                params = params,
                headers = headers
            )
            response.status.isSuccess()
        }
    }

    suspend inline fun <reified T : Any, reified R : Any> post(
        steamId: Long,
        handle: String,
        body: T,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): R {
        return executeWithAutoSwap(steamId) {
            awaitRateLimit(steamId)
            postRequest(
                httpClient = getClientForSteamId(steamId),
                handle = handle,
                params = params,
                headers = headers,
                body = body
            ).body()
        }
    }

    suspend inline fun <reified R : Any> postWithUrlEncodedForm(
        steamId: Long,
        handle: String,
        formParams: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): R {
        return executeWithAutoSwap(steamId) {
            awaitRateLimit(steamId)
            postRequestWithUrlEncodedForm(
                httpClient = getClientForSteamId(steamId),
                handle = handle,
                formParams = formParams,
                headers = headers
            ).body()
        }
    }

    suspend inline fun postWithUrlMultiParmFromAndReturnLocation(
        steamId: Long,
        handle: String,
        boundary: String,
        formText: ByteArray,
        headers: Map<String, String> = emptyMap()
    ): String? {
        return executeWithAutoSwap(steamId) {
            awaitRateLimit(steamId)
            postRequestWithMultiPartForm(
                httpClient = getClientForSteamId(steamId),
                handle = handle,
                boundary = boundary,
                formText = formText,
                headers = headers
            ).headers[HttpHeaders.Location]
        }
    }

    suspend inline fun <R> executeWithAutoSwap(steamId: Long, block: () -> R): R {

        var lastException: Exception? = null

        repeat(maxRetries) { attempt ->
            try {
                val result = block()
                recordSuccess(steamId)
                return result
            } catch (e: Exception) {
                lastException = e
                recordFailure(steamId, e.message)

                val proxyName = getCurrentProxyName(steamId)
                logger.warn("Request failed on proxy '$proxyName' (attempt ${attempt + 1}/$maxRetries): ${e.message}")

                if (attempt < maxRetries - 1) {
                    if (!isProxyHealthy(steamId)) {
                        logger.info("Proxy '$proxyName' is unhealthy, swapping...")
                        swapProxyForSteamId(steamId)
                    } else if (shouldSwapOnError(e)) {
                        logger.info("Swapping proxy due to error type: ${e::class.simpleName}")
                        swapProxyForSteamId(steamId)
                    }
                }
            }
        }

        throw lastException ?: error("Request failed after $maxRetries attempts")
    }

    open fun shouldSwapOnError(e: Exception): Boolean {
        val message = e.message?.lowercase() ?: ""
        return message.contains("timeout") ||
                message.contains("connection") ||
                message.contains("refused") ||
                message.contains("reset") ||
                message.contains("proxy") ||
                message.contains("503") ||
                message.contains("502") ||
                message.contains("429")
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
            mobileProxyManager.removeAssignment(steamId)
        }
    }

    companion object {
        private const val DEFAULT_MAX_RETRIES = 3
    }
}
