package dmitriy.losev.cs.clients

import dmitriy.losev.cs.proxy.MobileProxyManager
import dmitriy.losev.cs.proxy.ProxyRateLimiter
import dmitriy.losev.cs.proxy.Service
import io.ktor.http.URLProtocol
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Singleton

@Singleton
class CsFloatProxyClient(
    @Provided httpClientHandler: HttpClientHandler,
    @Provided mobileProxyManager: MobileProxyManager,
    @Provided proxyRateLimiter: ProxyRateLimiter
) : BaseMobileProxyClient(httpClientHandler, mobileProxyManager, proxyRateLimiter) {

    override val service = Service.CS_FLOAT

    override val protocol = URLProtocol.HTTPS

    override val rateLimitMs: Long = 1000L

    override val defaultHeaders = mapOf(
        "Accept" to "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
        "User-Agent" to "Mozilla/5.0 (Linux; Android 15; 2501FPN6DC) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.7499.52 Mobile Safari/537.36",
        "Cache-Control" to "max-age=0",
        "Connection" to "keep-alive",
        "Sec-Fetch-Dest" to "empty",
        "Sec-Fetch-Mode" to "navigate",
        "Sec-Fetch-Site" to "cross-site",
        "Upgrade-Insecure-Requests" to "1",
        "sec-ch-ua" to "Google Chrome\";v=\"143\", \"Chromium\";v=\"143\", \"Not_A Brand\";v=\"24",
        "sec-ch-ua-mobile" to "?1",
        "sec-ch-ua-platform" to "\"Android\"",
        "sec-ch-ua-full-version-list" to "Google Chrome\";v=\"143.0.7499.52\", \"Chromium\";v=\"143.0.7499.52\", \"Not_A Brand\";v=\"24.0.0.0",
        "sec-ch-ua-model" to "2501FPN6DC",
        "sec-ch-ua-platform-version" to "15.0.0",
        "Accept-Language" to "en-US,en;q=0.9",
        "Accept-Encoding" to "gzip, deflate",
    )
}
