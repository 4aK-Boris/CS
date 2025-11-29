package dmitriy.losev.cs

import dmitriy.losev.cs.proxy.ProxyConfig
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

data class Context(
    val coroutineContext: CoroutineContext = Dispatchers.IO,
    val environment: Environment = Environment.Production,
    val credentials: Credentials = Credentials(),
    val connectTimeoutMillis: Long = 600_000L,
    val socketTimeoutMillis: Long = 600_000L,
    val requestTimeoutMillis: Long = 600_000L,
    val steamConfig: SteamConfig = SteamConfig(),
    val defaultValues: DefaultValues = DefaultValues(),
    val proxyConfigs: List<ProxyConfig> = emptyList(),
    val pulseConfig: PulseConfig = PulseConfig(),
)