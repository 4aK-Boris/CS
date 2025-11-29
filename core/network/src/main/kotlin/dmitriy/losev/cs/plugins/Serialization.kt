package dmitriy.losev.cs.plugins

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal fun HttpClientConfig<OkHttpConfig>.configureJson() {
    install(plugin = ContentNegotiation) {
        json(
            json = Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                encodeDefaults = true
                coerceInputValues = true
                useAlternativeNames = false
                explicitNulls = true
            }
        )
    }
}

