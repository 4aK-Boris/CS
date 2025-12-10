package dmitriy.losev.cs.proxy

import java.time.Instant
import java.util.concurrent.atomic.AtomicLong

class MobileProxyStats(
    val deviceId: String,
    val deviceName: String
) {
    private val _successCount = AtomicLong(0)
    private val _failureCount = AtomicLong(0)
    private val _totalRequests = AtomicLong(0)

    @Volatile
    var lastSuccessTime: Instant? = null
        private set

    @Volatile
    var lastFailureTime: Instant? = null
        private set

    @Volatile
    var lastError: String? = null
        private set

    @Volatile
    var consecutiveFailures: Int = 0
        private set

    @Volatile
    var isBlocked: Boolean = false
        private set

    val successCount: Long get() = _successCount.get()
    val failureCount: Long get() = _failureCount.get()
    val totalRequests: Long get() = _totalRequests.get()

    val successRate: Double
        get() {
            val total = totalRequests
            return if (total > 0) successCount.toDouble() / total else 1.0
        }

    val isHealthy: Boolean
        get() = !isBlocked && consecutiveFailures < UNHEALTHY_THRESHOLD && successRate >= MIN_SUCCESS_RATE

    val isAvailable: Boolean
        get() = !isBlocked

    fun block() {
        isBlocked = true
    }

    fun unblock() {
        isBlocked = false
    }

    fun recordSuccess() {
        _successCount.incrementAndGet()
        _totalRequests.incrementAndGet()
        lastSuccessTime = Instant.now()
        consecutiveFailures = 0
    }

    fun recordFailure(error: String? = null) {
        _failureCount.incrementAndGet()
        _totalRequests.incrementAndGet()
        lastFailureTime = Instant.now()
        lastError = error
        consecutiveFailures++
    }

    fun resetStats() {
        _successCount.set(0)
        _failureCount.set(0)
        _totalRequests.set(0)
        consecutiveFailures = 0
        lastError = null
    }

    companion object {
        private const val UNHEALTHY_THRESHOLD = 5
        private const val MIN_SUCCESS_RATE = 0.5
    }
}
