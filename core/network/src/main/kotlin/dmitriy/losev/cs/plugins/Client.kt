package dmitriy.losev.cs.plugins

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttpConfig
import java.util.concurrent.TimeUnit
import okhttp3.ConnectionPool
import okhttp3.ConnectionSpec
import okhttp3.Dispatcher
import okhttp3.Protocol

internal fun HttpClientConfig<OkHttpConfig>.configureClient() {
    engine {
        config {

            dispatcher(okHttpDispatcher)

            protocols(protocols = listOf(Protocol.HTTP_2, Protocol.HTTP_1_1))

            connectionSpecs(connectionSpecs = listOf(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT))

            callTimeout(timeout = 35, unit = TimeUnit.SECONDS)
            connectTimeout(timeout = 30, unit = TimeUnit.SECONDS)
            readTimeout(timeout = 30, unit = TimeUnit.SECONDS)
            writeTimeout(timeout = 30, unit = TimeUnit.SECONDS)

            connectionPool(ConnectionPool(maxIdleConnections = 8, keepAliveDuration = 4, timeUnit = TimeUnit.MINUTES))

            followRedirects(followRedirects = false)

            retryOnConnectionFailure(retryOnConnectionFailure = true)
        }
    }
}

private val okHttpDispatcher = Dispatcher().apply {
    maxRequests = 100
    maxRequestsPerHost = 100
}
