package dmitriy.losev.cs.clients

//abstract class ProxyNetworkClient(private val proxyClients: ProxyClients) {
//
//    private val mutex = Mutex()
//
//    abstract val service: Service
//
//    open val protocol = URLProtocol.HTTPS
//
//    open val headers = mapOf<String, String>()
//
//    companion object {
//        private const val RATE_LIMIT_MS = 3000L
//        private const val FREEZE_DURATION_MS = 60000L
//
//        private fun formatException(e: Exception): String {
//            return when (e) {
//                is ClientRequestException -> "HTTP ${e.response.status.value} ${e.response.status.description}"
//                is HttpRequestTimeoutException -> "Request timeout (${e.message})"
//                is ConnectTimeoutException -> "Connection timeout - proxy not responding"
//                is SocketTimeoutException -> "Socket timeout - slow response"
//                is SocketException -> "Socket error: ${e.message}"
//                is UnknownHostException -> "Unknown host: ${e.message}"
//                else -> {
//                    val cause = e.cause
//                    val causeInfo = if (cause != null && cause != e) {
//                        " | Cause: ${cause::class.simpleName}: ${cause.message}"
//                    } else ""
//                    "${e::class.simpleName}: ${e.message ?: "Unknown error"}$causeInfo"
//                }
//            }
//        }
//    }
//
//    private suspend fun selectBestProxy(): ProxyClient {
//
//        return mutex.withLock {
//
//            // Фильтруем незамороженные и здоровые прокси
//            val availableProxies = proxyClients.clients.filter { proxyClient ->
//                val state = proxyClient.getState(service)
//                state.isHealthy && !state.isFrozen()
//            }
//
//            if (availableProxies.isEmpty()) {
//                // Если все прокси недоступны, сбрасываем флаги здоровья и ищем незамороженные
//                proxyClients.clients.forEach { proxyClient -> proxyClient.getState(service).isHealthy = true }
//                val unfrozenProxies = proxyClients.clients.filter { !it.getState(service).isFrozen() }
//
//                if (unfrozenProxies.isEmpty()) {
//                    // Если все прокси заморожены, возвращаем первый (придется подождать)
//                    proxyClients.clients.first()
//                } else {
//                    unfrozenProxies.first()
//                }
//            } else {
//                availableProxies.maxByOrNull { proxyClient ->
//
//                    val successRate = if (proxyClient.getState(service).requestCount > 0) {
//                        proxyClient.getState(service).successCount.toFloat() / proxyClient.getState(service).requestCount
//                    } else {
//                        1f
//                    }
//
//                    val timeSinceLastUse = System.currentTimeMillis() - proxyClient.getState(service).lastUsed
//                    val responseTimeFactor = 1000f / (proxyClient.getState(service).avgResponseTime + 1)
//
//                    successRate * timeSinceLastUse * responseTimeFactor
//
//                } ?: availableProxies.first()
//            }
//        }
//    }
//
//    private suspend fun waitForRateLimit(proxyClient: ProxyClient) {
//        val state = proxyClient.getState(service)
//        val timeSinceLastUse = System.currentTimeMillis() - state.lastUsed
//
//        if (timeSinceLastUse < RATE_LIMIT_MS) {
//            val delayTime = RATE_LIMIT_MS - timeSinceLastUse
//            println("Rate limit: waiting ${delayTime}ms for proxy ${proxyClient.config.name}")
//            delay(delayTime)
//        }
//    }
//
//    suspend fun <T : Any> get(
//        handle: String,
//        maxRetries: Int = 20,
//        responseClazz: KClass<T>,
//        params: Map<String, String> = emptyMap()
//    ): T {
//
//        var lastException: Exception? = null
//
//        repeat(maxRetries) { attempt ->
//
//            val proxyClient = selectBestProxy()
//
//            waitForRateLimit(proxyClient)
//
//            val startTime = System.currentTimeMillis()
//
//            try {
//                println("[${service.name}] GET request to $handle via proxy ${proxyClient.config.name} (${proxyClient.config.host}:${proxyClient.config.port})")
//
//                val responseBody = proxyClient.client.get {
//                    setUrl(handle)
//                    setParams(params)
//                    setHeaders(this@ProxyNetworkClient.headers)
//                    setCookie()
//                }.body<T>(typeInfo = TypeInfo(type = responseClazz))
//
//                val responseTime = System.currentTimeMillis() - startTime
//
//                println("[${service.name}] GET request SUCCESS via proxy ${proxyClient.config.name}, response time: ${responseTime}ms")
//
//                updateStats(proxyClient, success = true, responseTime)
//
//                return responseBody
//            } catch (e: Exception) {
//
//                lastException = e
//
//                val responseTime = System.currentTimeMillis() - startTime
//
//                // Детальное логирование ошибки
//                val errorDetails = formatException(e)
//                println("[${service.name}] GET request FAILED via proxy ${proxyClient.config.name}: $errorDetails")
//
//                // Проверяем на код 429 (Too Many Requests)
//                if (e is ClientRequestException && e.response.status == HttpStatusCode.TooManyRequests) {
//                    println("[${service.name}] 429 Too Many Requests for proxy ${proxyClient.config.name}. Freezing for ${FREEZE_DURATION_MS}ms")
//                    mutex.withLock {
//                        proxyClient.getState(service).freeze(FREEZE_DURATION_MS)
//                    }
//                }
//
//                updateStats(proxyClient, success = false, responseTime)
//
//                if (attempt < maxRetries - 1) {
//                    println("[${service.name}] Retrying in ${1000 * (attempt + 1)}ms... (attempt ${attempt + 2}/$maxRetries)")
//                    delay(1000)
//                }
//            }
//        }
//
//        throw lastException ?: Exception("Все попытки исчерпаны")
//    }
//
//    suspend fun <T : Any, R : Any> post(
//        handle: String,
//        maxRetries: Int = 20,
//        requestClazz: KClass<T>,
//        responseClazz: KClass<R>,
//        body: T,
//        params: Map<String, String> = emptyMap()
//    ): R {
//
//        var lastException: Exception? = null
//
//        repeat(maxRetries) { attempt ->
//
//            val proxyClient = selectBestProxy()
//
//            // Ожидание rate limit перед запросом
//            waitForRateLimit(proxyClient)
//
//            val startTime = System.currentTimeMillis()
//
//            try {
//                println("[${service.name}] POST request to $handle via proxy ${proxyClient.config.name} (${proxyClient.config.host}:${proxyClient.config.port})")
//
//                val responseBody = proxyClient.client.post {
//                    setBody(body = body, bodyType = TypeInfo(type = requestClazz))
//                    setUrl(handle)
//                    setParams(params)
//                    setHeaders(this@ProxyNetworkClient.headers)
//                }.body<R>(typeInfo = TypeInfo(type = responseClazz))
//
//                val responseTime = System.currentTimeMillis() - startTime
//
//                println("[${service.name}] POST request SUCCESS via proxy ${proxyClient.config.name}, response time: ${responseTime}ms")
//
//                updateStats(proxyClient, success = true, responseTime)
//
//                return responseBody
//            } catch (e: Exception) {
//
//                lastException = e
//
//                val responseTime = System.currentTimeMillis() - startTime
//
//                // Детальное логирование ошибки
//                val errorDetails = formatException(e)
//                println("[${service.name}] POST request FAILED via proxy ${proxyClient.config.name}: $errorDetails")
//
//                // Проверяем на код 429 (Too Many Requests)
//                if (e is ClientRequestException && e.response.status == HttpStatusCode.TooManyRequests) {
//                    println("[${service.name}] 429 Too Many Requests for proxy ${proxyClient.config.name}. Freezing for ${FREEZE_DURATION_MS}ms")
//                    mutex.withLock {
//                        proxyClient.getState(service).freeze(FREEZE_DURATION_MS)
//                    }
//                }
//
//                updateStats(proxyClient, success = false, responseTime)
//
//                if (attempt < maxRetries - 1) {
//                    println("[${service.name}] Retrying in ${1000 * (attempt + 1)}ms... (attempt ${attempt + 2}/$maxRetries)")
//                    delay(1000)
//                }
//            }
//        }
//
//        throw lastException ?: Exception("Все попытки исчерпаны")
//    }
//
//    private fun HttpRequestBuilder.setUrl(handle: String) {
//        url {
//            protocol = this@ProxyNetworkClient.protocol
//            host = service.host
//            path(handle)
//        }
//    }
//
//    private fun HttpRequestBuilder.setParams(params: Map<String, String>) {
//        params.forEach { (name, value) ->
//            parameter(key = name, value = value)
//        }
//    }
//
//    private fun HttpRequestBuilder.setHeaders(headers: Map<String, String>) {
//        headers.forEach { (name, value) ->
//            header(key = name, value = value)
//        }
//    }
//
//    private fun HttpRequestBuilder.setCookie() {
//
//    }
//
//    private suspend fun updateStats(
//        proxyClient: ProxyClient,
//        success: Boolean,
//        responseTime: Long
//    ) {
//        mutex.withLock {
//
//            proxyClient.getState(service).requestCount++
//            proxyClient.getState(service).lastUsed = System.currentTimeMillis()
//
//            if (success) {
//                proxyClient.getState(service).successCount++
//
//                val count = proxyClient.getState(service).successCount
//
//
//
//            } else {
//
//                proxyClient.getState(service).failureCount++
//
//
//
//                if (failureRate > 0.5 && proxyClient.getState(service).requestCount > 5) {
//                    proxyClient.getState(service).isHealthy = false
//                }
//            }
//        }
//    }
//
//    suspend fun getStatsForService(): List<ProxyStats> {
//
//        return mutex.withLock {
//
//            proxyClients.clients.map { proxyClient ->
//
//                val successRate = if (proxyClient.getState(service).requestCount > 0) {
//                    (proxyClient.getState(service).successCount.toFloat() / proxyClient.getState(service).requestCount * 100).toInt()
//                } else {
//                    0
//                }
//
//                ProxyStats(
//                    name = proxyClient.config.name,
//                    requestCount = proxyClient.getState(service).requestCount,
//                    successCount = proxyClient.getState(service).successCount,
//                    failureCount = proxyClient.getState(service).failureCount,
//                    avgResponseTime = proxyClient.getState(service).avgResponseTime,
//                    isHealthy = proxyClient.getState(service).isHealthy,
//                    successRate = successRate
//                )
//
//            }
//        }
//    }
//
//    fun closeAllForService() {
//        proxyClients.clients.forEach { proxyClient -> proxyClient.client.close() }
//    }
//}
