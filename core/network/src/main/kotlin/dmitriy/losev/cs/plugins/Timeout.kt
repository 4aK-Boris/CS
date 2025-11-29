package dmitriy.losev.cs.plugins

import dmitriy.losev.cs.Context
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.plugins.HttpTimeout

internal fun HttpClientConfig<OkHttpConfig>.configureTimeout(context: Context) {
    install(plugin = HttpTimeout) {
        connectTimeoutMillis = context.connectTimeoutMillis
        socketTimeoutMillis = context.socketTimeoutMillis
        requestTimeoutMillis = context.requestTimeoutMillis
    }
}

