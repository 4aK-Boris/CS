package dmitriy.losev.cs.plugins

import dmitriy.losev.cs.exceptions.NetworkException
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess

internal fun HttpClientConfig<OkHttpConfig>.configureResponseValidation() {

    HttpResponseValidator {
        validateResponse { response ->
            val status = response.status
            val isRedirect = status.value in 300..399
            if (status.isSuccess().not() && isRedirect.not()) {
                throw NetworkException(message = response.bodyAsText())
            }
        }
    }
}
