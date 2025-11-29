package dmitriy.losev.cs.clients

import dmitriy.losev.cs.proxy.ProxyClients
import dmitriy.losev.cs.proxy.Service
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import org.koin.core.annotation.Singleton

@Singleton
class CSMarketProxyNetworkClient(proxyClients: ProxyClients): ProxyNetworkClient(proxyClients) {

    override val service = Service.CS_MARKET

    override val protocol = URLProtocol.HTTPS

    override val headers = mapOf(
        "Authority" to "steamcommunity.com",
        HttpHeaders.Accept to "*/*",
        HttpHeaders.AcceptLanguage to "en-US,en;q=0.9,ru;q=0.8",
        HttpHeaders.CacheControl to "no-cache",
        HttpHeaders.Pragma to "no-cache",
        "sec-ch-ua" to "\"Not_A Brand\";v=\"8\", \"Chromium\";v=\"120\", \"Google Chrome\";v=\"120\"",
        "sec-ch-ua-mobile" to "?0",
        "sec-ch-ua-platform" to "\"Windows\"",
        "sec-fetch-dest" to "empty",
        "sec-fetch-mode" to "cors",
        "sec-fetch-site" to "same-origin",
        HttpHeaders.UserAgent to "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36",
        "x-requested-with" to "XMLHttpRequest",
    )
}