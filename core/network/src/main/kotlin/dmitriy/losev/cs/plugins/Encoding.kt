package dmitriy.losev.cs.plugins

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.plugins.compression.ContentEncoding

internal fun HttpClientConfig<OkHttpConfig>.configureEncoding() {
    install(plugin = ContentEncoding) {
        gzip(quality = 0.9f)
        deflate(quality = 0.9f)
        identity(quality = 0.9f)
    }
}

