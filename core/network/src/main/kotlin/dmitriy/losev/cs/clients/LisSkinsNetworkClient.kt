package dmitriy.losev.cs.clients

import dmitriy.losev.cs.proxy.Service
import io.ktor.http.URLProtocol
import org.koin.core.annotation.Singleton

@Singleton
class LisSkinsNetworkClient(httpClientHandler: HttpClientHandler): NetworkClient(httpClientHandler) {

    override val service = Service.LIS_SKINS

    override val protocol = URLProtocol.HTTPS
}
