package dmitriy.losev.cs.routes

import dmitriy.losev.cs.deleteHandle
import dmitriy.losev.cs.descriptions.ProxyDescription
import dmitriy.losev.cs.extractors.ProxyExtractor
import dmitriy.losev.cs.getHandle
import dmitriy.losev.cs.postHandle
import dmitriy.losev.cs.services.ProxyService
import dmitriy.losev.cs.validations.ProxyValidation
import io.ktor.server.routing.Routing
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Routing.configureProxyRouting() {

    val proxyService by inject<ProxyService>()

    val proxyDescription by inject<ProxyDescription>()

    val proxyValidation by inject<ProxyValidation>()

    val proxyExtractor by inject<ProxyExtractor>()

    route(path = "/api/proxy") {

        postHandle(
            builder = proxyDescription::upsertProxyConfigsDescription,
            validation = proxyValidation.validateUpsertProxyConfigs,
            processing = proxyService::upsertProxyConfigs
        )

        deleteHandle(
            builder = proxyDescription::deleteProxyConfigDescription,
            validation = proxyValidation.validateDeleteProxyConfig,
            extractor = proxyExtractor::extractProxyHostAndPort,
            processing = proxyService::deleteProxyConfig
        )

        getHandle(
            builder = proxyDescription::getProxyConfigsDescription,
            processing = proxyService::getProxyConfigs
        )

        route(path = "/steam") {

            getHandle(
                builder = proxyDescription::getSteamAccountProxyConfigsDescription,
                processing = proxyService::getSteamAccountProxyConfigs
            )
        }
    }
}
