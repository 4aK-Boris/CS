package dmitriy.losev.cs.core

import dmitriy.losev.cs.HttpLoggingConfig
import io.ktor.http.Headers
import io.ktor.http.content.OutgoingContent
import io.ktor.http.content.TextContent
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import io.ktor.server.request.uri
import io.ktor.util.AttributeKey
import io.ktor.util.toMap
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.toByteArray
import org.slf4j.LoggerFactory

private val CallStartTimeKey = AttributeKey<Long>(name = "CallStartTime")

fun createHttpLoggingPlugin(config: HttpLoggingConfig) = createApplicationPlugin(name = "HttpLogging") {

    val logger = LoggerFactory.getLogger("http")

    val excludedPaths = config.excludedPaths
    val sensitiveHeaders = config.sensitiveHeaders
    val sensitiveFields = config.sensitiveFields
    val maxBodySize = config.maxBodySize

    onCall { call ->
        call.attributes.put(CallStartTimeKey, System.currentTimeMillis())
    }

    onCallReceive { call, body ->
        val path = call.request.path()
        if (!shouldLog(excludedPaths, path)) return@onCallReceive

        val request = call.request
        val requestBody = when (body) {
            is ByteReadChannel -> body.tryReadText() ?: "[binary]"
            else -> body.toString()
        }

        logger.debug(
            """
            |
            |>>> REQUEST >>>
            |${request.httpMethod.value} ${request.uri}
            |Headers: ${formatHeaders(sensitiveHeaders, request.headers)}
            |Query: ${request.queryParameters.toMap()}
            |Body: ${processBody(maxBodySize, sensitiveFields, requestBody)}
            """.trimMargin()
        )
    }

    onCallRespond { call, body ->
        val path = call.request.path()
        if (!shouldLog(excludedPaths, path)) return@onCallRespond

        val responseBody = when (body) {
            is TextContent -> body.text
            is OutgoingContent.ByteArrayContent -> body.bytes().decodeToString()
            is OutgoingContent.ReadChannelContent -> "[stream]"
            is OutgoingContent.WriteChannelContent -> "[stream]"
            is OutgoingContent.NoContent -> "[no content]"
            else -> body.toString()
        }

        val startTime = call.attributes.getOrNull(CallStartTimeKey)
        val duration = startTime?.let { System.currentTimeMillis() - it } ?: -1

        logger.debug(
            """
            |
            |<<< RESPONSE <<< (${duration}ms)
            |${call.request.httpMethod.value} ${call.request.uri}
            |Status: ${call.response.status()}
            |Body: ${processBody(maxBodySize, sensitiveFields, responseBody)}
            """.trimMargin()
        )
    }
}

private fun formatHeaders(sensitiveHeaders: List<String>, headers: Headers): Map<String, List<String>> {
    return headers.toMap().mapValues { (key, values) ->
        if (key.lowercase() in sensitiveHeaders) listOf("***") else values
    }
}

private fun processBody(maxBodySize: Int, sensitiveFields: List<String>, body: String): String {
    return truncate(maxBodySize, maskSensitiveData(sensitiveFields, body))
}

private fun maskSensitiveData(sensitiveFields: List<String>, body: String): String {

    var masked = body

    sensitiveFields.forEach { field ->
        masked = masked.replace(regex = Regex(""""$field"\s*:\s*"[^"]*"""", RegexOption.IGNORE_CASE), replacement = """"$field": "***"""")
        masked = masked.replace(regex = Regex(""""$field"\s*:\s*\d+""", RegexOption.IGNORE_CASE), replacement = """"$field": "***"""")
        masked = masked.replace(regex = Regex("""(?<=[?&]|^)$field=[^&]*""", RegexOption.IGNORE_CASE), replacement = "$field=***")
        masked = masked.replace(regex = Regex("""$field=[^,)]+""", RegexOption.IGNORE_CASE), replacement = "$field=***")
    }

    return masked
}

private fun truncate(maxBodySize: Int, body: String): String {
    return if (body.length > maxBodySize) {
        body.take(maxBodySize) + "... [truncated]"
    } else {
        body
    }
}

private fun shouldLog(excludedPaths: List<String>, path: String): Boolean {
    return excludedPaths.none { excludedPath -> path.startsWith(excludedPath) }
}

private suspend fun ByteReadChannel.tryReadText(): String? {
    return try {
        toByteArray().decodeToString()
    } catch (_: Exception) {
        null
    }
}
