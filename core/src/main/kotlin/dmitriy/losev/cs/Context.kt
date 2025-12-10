package dmitriy.losev.cs

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

data class Context(
    val coroutineContext: CoroutineContext = Dispatchers.IO,
    val environment: Environment = Environment.Production,
    val connectTimeoutMillis: Long = 600_000L,
    val socketTimeoutMillis: Long = 600_000L,
    val requestTimeoutMillis: Long = 600_000L,
    val steamConfig: SteamConfig = SteamConfig(),
    val pulseConfig: PulseConfig = PulseConfig(),
    val databaseConfig: DatabaseConfig = DatabaseConfig(),
    val httpLoggingConfig: HttpLoggingConfig = HttpLoggingConfig(),
    val mobileProxyConfig: MobileProxyConfig = MobileProxyConfig(),
    val aesKey: String = EMPTY_STRING,
    val debug: Boolean = false
)
