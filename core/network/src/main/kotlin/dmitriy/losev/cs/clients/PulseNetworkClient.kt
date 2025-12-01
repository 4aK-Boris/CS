package dmitriy.losev.cs.clients

import dmitriy.losev.cs.proxy.Service
import io.ktor.http.URLProtocol
import org.koin.core.annotation.Singleton

@Singleton
class PulseNetworkClient(httpClientHandler: HttpClientHandler): NetworkClient(httpClientHandler) {

    override val service = Service.PULSE

    override val protocol = URLProtocol.HTTPS

    override val defaultHeaders = mapOf(
        "Host" to "api-pulse.tradeon.space",
        "Connection" to "keep-alive",
        "sec-ch-ua-platform" to "\"Windows\"",
        "Device-Id" to "368710d765abbd7741380e0e7c0eb960",
        "sec-ch-ua" to "\"Not)A;Brand\";v=\"8\", \"Chromium\";v=\"138\", \"YaBrowser\";v=\"25.8\", \"Yowser\";v=\"2.5\"",
        "sec-ch-ua-mobile" to "?0",
        "User-Agent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 YaBrowser/25.8.0.0 Safari/537.36",
        "Accept" to "*/*",
        "Origin" to "https://pulse.tradeon.space",
        "Sec-Fetch-Site" to "same-site",
        "Sec-Fetch-Mode" to "cors",
        "Sec-Fetch-Dest" to "empty",
        "Referer" to "https://pulse.tradeon.space/",
        "Accept-Language" to "ru,en;q=0.9,zh;q=0.8",
        "content-type" to "application/json"
    )
}
