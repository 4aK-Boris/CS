package dmitriy.losev.cs.plugins

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.LoggingFormat

internal fun HttpClientConfig<OkHttpConfig>.configureLogging() {
    install(plugin = Logging) {
        logger = Logger.DEFAULT
        format = LoggingFormat.Default
        level = LogLevel.ALL
    }
}