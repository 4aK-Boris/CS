package dmitriy.losev.cs.clients

import dmitriy.losev.cs.proxy.Service
import io.ktor.http.URLProtocol
import org.koin.core.annotation.Singleton

@Singleton
class FloatNetworkClient(httpClientHandler: HttpClientHandler): NetworkClient(httpClientHandler) {

    override val service = Service.CsFloat

    override val protocol = URLProtocol.HTTP
}
