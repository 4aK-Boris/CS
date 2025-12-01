package dmitriy.losev.cs.proxy

data class ProxyStats(
    val name: String,
    val requestCount: Int,
    val successCount: Int,
    val failureCount: Int,
    val avgResponseTime: Long,
    val isHealthy: Boolean,
    val successRate: Int
)
