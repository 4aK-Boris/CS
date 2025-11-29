package dmitriy.losev.cs.proxy

import io.ktor.client.HttpClient

data class ProxyClient(
    val client: HttpClient,
    val config: ProxyConfig,
    val state: Map<Service, ProxyState> = Service.entries.associateWith { ProxyState() }
) {

    fun getState(service: Service): ProxyState {
        return state.getValue(service)
    }

    data class ProxyState(
        var requestCount: Int = 0,
        var successCount: Int = 0,
        var failureCount: Int = 0,
        var lastUsed: Long = 0,
        var avgResponseTime: Long = 0,
        var isHealthy: Boolean = true,
        var frozenUntil: Long = 0  // Время до которого прокси заморожен (в миллисекундах)
    ) {
        fun isFrozen(): Boolean = System.currentTimeMillis() < frozenUntil

        fun freeze(durationMs: Long) {
            frozenUntil = System.currentTimeMillis() + durationMs
        }
    }
}