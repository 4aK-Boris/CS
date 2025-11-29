package dmitriy.losev.cs.proxy

import dmitriy.losev.cs.Context
import dmitriy.losev.cs.clients.HttpClientHandler

class ProxyClients(
    context: Context,
    private val httpClientHandler: HttpClientHandler
) {

    val clients: MutableList<ProxyClient> =
        context.proxyConfigs.map { proxyConfig ->
            ProxyClient(
                client = httpClientHandler.getProxyHttpClient(proxyConfig),
                config = proxyConfig
            )
        }.toMutableList()
}