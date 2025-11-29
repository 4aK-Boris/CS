package dmitriy.losev.cs.plugins

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.plugins.HttpRequestRetry
import java.io.IOException

internal fun HttpClientConfig<OkHttpConfig>.configureRequestRetry() {
    install(plugin = HttpRequestRetry) {
        maxRetries = 3
        retryIf { _, response -> response.status.value in 500..599 }
        retryOnExceptionIf { _, cause -> cause is IOException }
        exponentialDelay(base = 250.0, maxDelayMs = 2_000)
        retryOnServerErrors(maxRetries = 5)
    }
}