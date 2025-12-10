package dmitriy.losev.cs.clients

import dmitriy.losev.cs.proxy.Service
import io.ktor.http.URLProtocol
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Singleton

@Singleton
class SteamCommunityProxyClient(@Provided httpClientHandler: HttpClientHandler): BaseProxyClient(httpClientHandler) {

    override val service = Service.STEAM_COMMUNITY

    override val protocol = URLProtocol.HTTPS

    override val defaultHeaders = mapOf(
        "User-Agent" to "Dalvik/2.1.0 (Linux; U; Android 15; 2501FPN6DC Build/AP3A.241105.008)",
        "Accept" to "*/*",
        "Accept-Language" to "ru,en;q=0.9,en-GB;q=0.8,en-US;q=0.7",
        "Accept-Encoding" to "gzip, deflate",
        "Connection" to "keep-alive"
    )
}
