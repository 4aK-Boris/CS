package dmitriy.losev.cs.plugins

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttpConfig
import java.net.InetSocketAddress
import java.net.Proxy
import okhttp3.Authenticator
import okhttp3.Credentials
import okhttp3.Route

internal fun HttpClientConfig<OkHttpConfig>.configureProxy(proxyConfig: ProxyConfig) {

    engine {
        proxy = Proxy(
            Proxy.Type.HTTP,
            InetSocketAddress(proxyConfig.host, proxyConfig.port)
        )

        config {

            addInterceptor { chain ->

                val credential = Credentials.basic(proxyConfig.login, proxyConfig.password)

                val request = chain.request().newBuilder()
                    .header("Proxy-Authorization", credential)
                    .build()

                chain.proceed(request)
            }

            proxyAuthenticator(

                object : Authenticator {

                    override fun authenticate(route: Route?, response: okhttp3.Response): okhttp3.Request? {
                        if (response.code == 407) {
                            val credential = Credentials.basic(proxyConfig.login, proxyConfig.password)
                            return response.request.newBuilder()
                                .header("Proxy-Authorization", credential)
                                .build()
                        }
                        return null
                    }
                }

            )
        }
    }
}