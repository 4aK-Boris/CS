package dmitriy.losev.cs.proxy

import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.koin.core.annotation.Singleton
import java.util.concurrent.ConcurrentHashMap

@Singleton
class ProxyRateLimiter {

    private val lastRequestTime = ConcurrentHashMap<RateLimitKey, Long>()
    private val mutex = Mutex()

    suspend fun acquirePermit(deviceId: String, host: String, minIntervalMs: Long = DEFAULT_MIN_INTERVAL_MS) {
        val key = RateLimitKey(deviceId, host)

        mutex.withLock {
            val now = System.currentTimeMillis()
            val lastTime = lastRequestTime[key] ?: 0L
            val elapsed = now - lastTime

            if (elapsed < minIntervalMs) {
                val waitTime = minIntervalMs - elapsed
                delay(waitTime)
            }

            lastRequestTime[key] = System.currentTimeMillis()
        }
    }

    fun clearForDevice(deviceId: String) {
        lastRequestTime.keys.removeIf { it.deviceId == deviceId }
    }

    fun clearAll() {
        lastRequestTime.clear()
    }

    private data class RateLimitKey(
        val deviceId: String,
        val host: String
    )

    companion object {
        const val DEFAULT_MIN_INTERVAL_MS = 1000L
    }
}
