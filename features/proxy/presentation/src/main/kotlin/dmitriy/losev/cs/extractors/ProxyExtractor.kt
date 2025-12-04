package dmitriy.losev.cs.extractors

import dmitriy.losev.cs.EMPTY_STRING
import dmitriy.losev.cs.core.PROXY_HOST_PARAMETER_NAME
import dmitriy.losev.cs.core.PROXY_PORT_PARAMETER_NAME
import dmitriy.losev.cs.models.DeleteProxyConfigRequestModel
import io.ktor.server.application.ApplicationCall
import org.koin.core.annotation.Singleton

@Singleton
class ProxyExtractor {

    fun extractProxyHostAndPort(call: ApplicationCall): DeleteProxyConfigRequestModel {
        val host = call.parameters[PROXY_HOST_PARAMETER_NAME] ?: EMPTY_STRING
        val port = call.parameters[PROXY_PORT_PARAMETER_NAME] ?: EMPTY_STRING
        return DeleteProxyConfigRequestModel(host, port.toInt())
    }
}
